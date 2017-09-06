package ming.stop.biz.front.web;

import ming.stop.base.BaseServlet;
import ming.stop.biz.front.service.OrderitemService;
import ming.stop.biz.front.service.OrdersService;
import ming.stop.biz.front.service.impl.OrderitemServiceImpl;
import ming.stop.biz.front.service.impl.OrdersServiceImpl;
import ming.stop.entity.*;
import ming.stop.utils.CommonUtils;
import ming.stop.utils.PaymentUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Ming on 2017/8/24.
 */
@WebServlet(urlPatterns = {"/orders"})
public class OrdersServlet extends BaseServlet {
    OrdersService ordersService = new OrdersServiceImpl();
    OrderitemService orderitemService = new OrderitemServiceImpl();
    // 提交订单
    public void submitOrder(HttpServletRequest request , HttpServletResponse response)  {
        try {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            Double totalPrice = (Double)session.getAttribute("totalPrice");
            if(totalPrice == null || user == null)  { // 超过30分钟没操作 或未登录
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                return;
            }
            //封装Orders
            Orders order = new Orders();
            order.setState(0);//未付款
            order.setOid(CommonUtils.getUUID());
            order.setUser(user);
            order.setOrdertime(new Date());
            order.setName(user.getName());
            order.setTelephone(user.getTelephone());
            order.setAddress(user.getEmail());
            Map <String,CartItem> cartMap = (LinkedHashMap<String, CartItem>) session.getAttribute("cartMap");
            //封装 orderitems
            List<Orderitem> orderitems = new ArrayList<>();
            Set<Map.Entry<String, CartItem>> entries = cartMap.entrySet();
            for (Map.Entry<String, CartItem> entry : entries) {
                Orderitem orderitem = new Orderitem();
                orderitem.setItemid(CommonUtils.getUUID());
                orderitem.setSubtotal(entry.getValue().getSubtotal());
                orderitem.setProduct(entry.getValue().getProduct());
                orderitem.setCount(entry.getValue().getQuantity());
                orderitem.setOrders(order);//每个订单项都属于同一个订单
                orderitems.add(orderitem);//封装成一个 订单的orderitems属性
            }
            order.setOrderitems(orderitems);
            order.setTotal(totalPrice);//订单的总价格
            //提交订单
            boolean b = ordersService.submitOrders(order);
            session.setAttribute("order",order);
            response.sendRedirect(request.getContextPath()+"/order_info.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 确认订单--- 更新Orders 和 在线支付
    public void pay(HttpServletRequest request , HttpServletResponse response){
        try {
            String oid = request.getParameter("oid");
            Orders orders = CommonUtils.copyToBean(request, Orders.class);
            ordersService.updateOrders(orders);//更新用户信息 （地址，姓名，手机号)

            //接入易宝支付
            // 获得 支付必须基本数据
            // 银行
            String pd_FrpId = request.getParameter("pd_FrpId");

            // 发给支付公司需要哪些数据
            String p0_Cmd = "Buy";
            String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
            String p2_Order = oid;
            //String p3_Amt = String.valueOf(orders.getTotal());//订单总价格
            String p3_Amt = "0.01";
            String p4_Cur = "CNY";
            String p5_Pid = "";
            String p6_Pcat = "";
            String p7_Pdesc = "";
            // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
            // 第三方支付可以访问网址
            String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
            String p9_SAF = "";
            String pa_MP = "";
            String pr_NeedResponse = "1";
            // 加密hmac 需要密钥
            String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
                    "keyValue");
            String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                    p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                    pd_FrpId, pr_NeedResponse, keyValue);

            String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
                    "&p0_Cmd="+p0_Cmd+
                    "&p1_MerId="+p1_MerId+
                    "&p2_Order="+p2_Order+
                    "&p3_Amt="+p3_Amt+
                    "&p4_Cur="+p4_Cur+
                    "&p5_Pid="+p5_Pid+
                    "&p6_Pcat="+p6_Pcat+
                    "&p7_Pdesc="+p7_Pdesc+
                    "&p8_Url="+p8_Url+
                    "&p9_SAF="+p9_SAF+
                    "&pa_MP="+pa_MP+
                    "&pr_NeedResponse="+pr_NeedResponse+
                    "&hmac="+hmac;

            //重定向到第三方支付平台
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  我的订单
    public void myOrders(HttpServletRequest request , HttpServletResponse response){
        try {
            //封装pageBean
            int currentPage = 1;
            int currentTotal = 5;
            String currentPageStr = request.getParameter("currentPage");
            if(currentPageStr != null) currentPage = Integer.parseInt(currentPageStr);
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");
            if(user != null) {
                PageBean<Orders> pageBean = ordersService.getPageBean(user,currentPage,currentTotal);
                request.setAttribute("OrdersBean",pageBean);
            }

            request.getRequestDispatcher("/order_list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   /* // 重新支付
    public void repay(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String oid = request.getParameter("oid");
        if(oid != null){
            Orders order = new Orders();

        }

    }*/
    // 取消订单
    public void  reOrder(HttpServletRequest request , HttpServletResponse response){
        try {
            String oid = request.getParameter("oid");
            //删除该订单和订单项
            if(oid != null) {
                ordersService.deleteOrders(oid);
            }
            response.sendRedirect(request.getContextPath()+"/orders?action=myOrders&currentPage=1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
