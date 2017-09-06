package ming.stop.biz.admin.web;

import com.google.gson.Gson;
import ming.stop.base.BaseServlet;
import ming.stop.biz.admin.service.OrderitemService;
import ming.stop.biz.admin.service.OrdersService;
import ming.stop.biz.admin.service.ProductService;
import ming.stop.biz.admin.service.impl.OrderitemServiceImpl;
import ming.stop.biz.admin.service.impl.OrdersServiceImpl;
import ming.stop.biz.admin.service.impl.ProductServiceImpl;
import ming.stop.entity.Orderitem;
import ming.stop.entity.Orders;
import ming.stop.entity.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Ming on 2017/9/2.
 */
@WebServlet(urlPatterns = {"/admin/orders"})
public class OrdersServlet extends BaseServlet{
    OrdersService ordersService = new OrdersServiceImpl();
    OrderitemService orderitemService = new OrderitemServiceImpl();
    ProductService productService = new ProductServiceImpl();
    //显示所有的订单
    public void show(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Orders> orders = ordersService.queryAllOrders();
            request.setAttribute("ordersList",orders);
            request.getRequestDispatcher("/admin/order/list.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 显示该订单所有的订单项
    public void showOrderitem(HttpServletRequest request, HttpServletResponse response){
        try {
            Orders orders = new Orders();
            orders.setOid(request.getParameter("oid"));
            List<Orderitem> orderitems = orderitemService.queryOrderitemByOrder(orders);
            for (Orderitem orderitem : orderitems) {
                Product product = productService.queryByid(orderitem.getPid());
                orderitem.setProduct(product);
            }
            Gson gson = new Gson();
            String str = gson.toJson(orderitems);
            //睡2秒
            Thread.sleep(800);
            response.getWriter().write(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
