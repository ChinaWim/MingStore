package ming.stop.biz.admin.web;

import ming.stop.base.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ming on 2017/9/3.
 */
@WebServlet(name = "UserServlet",urlPatterns = {"/admin/user"})
public class UserServlet extends BaseServlet {


    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username != null && password != null) {
                //先写死账号密码
                if (username.equals("Ming") && password.equals("root321")) {
                    response.sendRedirect(request.getContextPath() + "/admin/home.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
