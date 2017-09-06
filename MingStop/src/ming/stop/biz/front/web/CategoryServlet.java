package ming.stop.biz.front.web;

import com.google.gson.Gson;
import ming.stop.base.BaseServlet;
import ming.stop.biz.front.service.CategoryService;
import ming.stop.biz.front.service.impl.CategoryServiceImpl;
import ming.stop.entity.Category;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Ming on 2017/8/24.
 */
@WebServlet("/category")
public class CategoryServlet extends BaseServlet {

    //header 页面 导航的分类显示
    public void showCategory(HttpServletRequest request, HttpServletResponse response){
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> categories = null;
        try {
            categories = categoryService.queryAll();
            request.setAttribute("category",categories);
            Gson gson = new Gson();
            String str = gson.toJson(categories);
            response.getWriter().write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
