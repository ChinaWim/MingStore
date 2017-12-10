package ming.store.biz.front.web;

import com.google.gson.Gson;
import ming.store.base.BaseServlet;
import ming.store.biz.front.service.ProductService;
import ming.store.biz.front.service.impl.ProductServiceImpl;
import ming.store.entity.CartItem;
import ming.store.entity.PageBean;
import ming.store.entity.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by Ming on 2017/8/24.
 */
@WebServlet(urlPatterns = {"/product"})
public class ProductServlet extends BaseServlet {
    ProductService productService = new ProductServiceImpl();

    //分页处理 & 显示浏览记录
    public void productList(HttpServletRequest request , HttpServletResponse response){
        try {
            String cid = request.getParameter("cid");
            String currentPageStr = request.getParameter("currentPage");
            int currentPage = 1;
            if(currentPageStr != null) currentPage = Integer.parseInt(currentPageStr);
            int currentCount = 12;// 一个页面显示的数目
            //查找PageBean
            PageBean<Product> pageBean = productService.queryPageBeanByCid(cid, currentPage, currentCount);

            //查找 history
            Cookie[] cookies = request.getCookies();
            Cookie cookie = null;
            for (Cookie ck : cookies) {
                if(ck.getName().equals("history")){
                    cookie = ck;
                    break;
                }
            }
            if(cookie != null){
                String res = cookie.getValue();
                String[] pids = res.split("-");
                //去重复
                Set<String> pidSet = new LinkedHashSet<>();
                for (String pid : pids) {
                    pidSet.add(pid);
                }
                List<Product> history = new ArrayList<>();
                for (String pid : pidSet) {
                    Product product = productService.queryByid(pid);
                    history.add(product);
                }
                request.setAttribute("history",history);
            }

            request.setAttribute("pageBean",pageBean);
            request.setAttribute("cid",cid);
            request.getRequestDispatcher("/product_list.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 商品详细信息业务
    public void productInfo(HttpServletRequest request , HttpServletResponse response){
        try {
            String pid = request.getParameter("pid");
            String currentPage = request.getParameter("currentPage");//这个用于返回 列表
            Product product = productService.queryByid(pid);

            //存储历史记录
            Cookie[] cookies = request.getCookies();
            boolean flag = false;
            Cookie cookie = null;
            if(cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("history")) {
                        flag = true;//存在cookie
                        cookies[i].setValue(pid + "-" + cookies[i].getValue());
                        cookie = cookies[i];
                        break;
                    }
                }
            }
            if(!flag ) {
                cookie = new Cookie("history", pid);
            }
            response.addCookie(cookie);

            request.setAttribute("product",product);
            request.setAttribute("currentPage",currentPage);
            request.getRequestDispatcher("/product_info.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 购物车业务
    public void productCart(HttpServletRequest request , HttpServletResponse response){
        String quantity = request.getParameter("quantity");//数量
        String pid = request.getParameter("pid");
        try {
            // 封装CarItem 对象
            Product product = productService.queryByid(pid);
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(Integer.parseInt(quantity));
            double subtotal = product.getShop_price()*Integer.parseInt(quantity);
            cartItem.setSubtotal(subtotal);

            //构建购物车map
            HttpSession session = request.getSession();
            LinkedHashMap<String,CartItem> cartMap = (LinkedHashMap<String,CartItem> )session.getAttribute("cartMap");
            if(cartMap == null){//购物车不存在
                cartMap = new LinkedHashMap<>( );
                cartMap.put(product.getPid(),cartItem);
            }else {
                //购物车存在，并且存在商品,修改该商品的数量和商品的总价格
                if(cartMap.containsKey(pid)){
                    CartItem item = cartMap.get(pid);
                    item.setQuantity(item.getQuantity()+cartItem.getQuantity());
                    item.setSubtotal(item.getSubtotal()+cartItem.getSubtotal());
                }else{//购物车存在，商品不存在
                    cartMap.put(product.getPid(),cartItem);
                }
            }
            session.setAttribute("cartMap",cartMap);

            // 将 cartmap反顺序存一份到 session中 取得时候直接取反顺序的
            double totalPrice = 0;//购物车总价格
            LinkedHashMap<String,CartItem> reverseCartMap = new LinkedHashMap<>();
            ListIterator<Map.Entry<String, CartItem>> i =
                    new ArrayList<Map.Entry<String, CartItem>>(cartMap.entrySet()).listIterator(cartMap.size());
            while( i.hasPrevious()){
                Map.Entry<String, CartItem> entry = i.previous();
                reverseCartMap.put(entry.getKey(),entry.getValue());
                totalPrice += entry.getValue().getSubtotal();
            }
            session.setAttribute("reverseCartMap",reverseCartMap);
            session.setAttribute("totalPrice",totalPrice);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除购物车某一项
    public void delCartItem(HttpServletRequest request , HttpServletResponse response){
        try {
            String pid = request.getParameter("pid");
            HttpSession session = request.getSession();
            Map<String,CartItem> cartMap =(Map<String,CartItem>) session.getAttribute("cartMap");
            Map<String,CartItem> reverseCartMap =(Map<String,CartItem>) session.getAttribute("reverseCartMap");
            double totalPrice = (double)session.getAttribute("totalPrice");
            if(cartMap!= null){
                totalPrice -= cartMap.get(pid).getSubtotal();
                cartMap.remove(pid);
                reverseCartMap.remove(pid);
                session.setAttribute("cartMap",cartMap);
                session.setAttribute("reverseCartMap",reverseCartMap);
                session.setAttribute("totalPrice",totalPrice);
            }
            response.sendRedirect(request.getContextPath()+"/cart.jsp");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //删除购物车所有
    public void delCart(HttpServletRequest request , HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("cartMap");
        session.removeAttribute("reverseCartMap");
        session.removeAttribute("totalPrice");
        try {
            response.sendRedirect(request.getContextPath()+"/cart.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导航栏搜索商品，将返回值显示在下拉框
    public void search(HttpServletRequest request , HttpServletResponse response){
        String value = request.getParameter("searchValue");
        if(value != null ) {
            value = value.trim();
            ProductServiceImpl productService = new ProductServiceImpl();
            try {
                List<Object> list = productService.queryByName(value);
                if(list != null && list.size() > 0) {
                    Gson gson = new Gson();
                    String result = gson.toJson(list);
                    response.getWriter().write(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //提交搜索值，挑转到productInfo
    public void submitSearch(HttpServletRequest request , HttpServletResponse response){
        try {
            String pname = request.getParameter("pname");
            Product product = productService.queryByNameToProduct(pname);
            if(product != null){
                response.sendRedirect(request.getContextPath()+"/product?action=productInfo&pid="+product.getPid()+"&currentPage=1");
            }
            else{
                response.sendRedirect(request.getContextPath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
