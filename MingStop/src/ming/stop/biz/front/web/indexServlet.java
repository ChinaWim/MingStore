package ming.stop.biz.front.web;

import ming.stop.base.BaseServlet;
import ming.stop.biz.front.service.CategoryService;
import ming.stop.biz.front.service.impl.CategoryServiceImpl;
import ming.stop.biz.front.service.ProductService;
import ming.stop.biz.front.service.impl.ProductServiceImpl;
import ming.stop.entity.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Ming on 2017/8/25.
 */
@WebServlet(name = "indexServlet",urlPatterns = {"/index"})
public class indexServlet extends BaseServlet {
    // 首页 最新,最热商品显示
    public void product(HttpServletRequest request, HttpServletResponse response){
        ProductService productService = new ProductServiceImpl();
        CategoryService categoryService = new CategoryServiceImpl();
        try {
            List<Product> hotProduct = productService.queryHotProduct();
            List<Product> newProduct = productService.queryNewProduct();
            request.setAttribute("hotProduct",hotProduct);
            request.setAttribute("newProduct",newProduct);

            request.getRequestDispatcher("/index.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
