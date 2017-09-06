package ming.stop.biz.admin.web;

import ming.stop.base.BaseServlet;
import ming.stop.biz.admin.service.CategoryService;
import ming.stop.biz.admin.service.impl.CategoryServiceImpl;
import ming.stop.entity.Category;
import ming.stop.utils.CommonUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ming on 2017/9/1.
 */
@WebServlet(urlPatterns = {"/admin/category"})
public class CategoryServlet extends BaseServlet{
    CategoryService categoryService = new CategoryServiceImpl();
    //显示分类
    public void show(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Category> categories = categoryService.queryAll();
            request.setAttribute("categories",categories);
            request.getRequestDispatcher("/admin/category/list.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 添加分类
    public void add(HttpServletRequest request, HttpServletResponse response){
        try {
            Category category = new Category();
            category.setCid(CommonUtils.getUUID());
            category.setCname(request.getParameter("cname"));
            categoryService.addCategory(category);
            response.getWriter().write("添加成功！3秒后跳转首页");
            response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/category?action=show");
        } catch (Exception e) {
            try {
                response.getWriter().write("添加失败！3秒后跳转首页");
                response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/category?action=show");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    //更新分类
    public void edit(HttpServletRequest request, HttpServletResponse response){
        try {
            Category category = CommonUtils.copyToBean(request,Category.class);
            categoryService.updateCategory(category);
            response.getWriter().write("更新成功！ 3秒后跳转首页");
            response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/category?action=show");
        } catch (Exception e) {
            try {
                response.getWriter().write("更新失败！ 3秒后跳转首页");
                response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/category?action=show");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    //删除分类
    public void delete(HttpServletRequest request, HttpServletResponse response){
        try {
            String cid = request.getParameter("cid");
            categoryService.deleteCategoryById(cid);
            response.getWriter().write("删除成功！ 3秒后跳转首页");
            response.sendRedirect(request.getContextPath()+"/admin/category?action=show");
        } catch (Exception e) {
            try {
                response.getWriter().write("删除失败！ 3秒后跳转首页");
                response.setHeader("refresh", "3;" + request.getContextPath() + "/admin/category?action=show");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }


}
