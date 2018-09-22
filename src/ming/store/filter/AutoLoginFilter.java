package ming.store.filter;

import ming.store.biz.front.service.impl.UserServiceImpl;
import ming.store.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Ming on 2017/8/31.
 */
@WebFilter(filterName = "AutoLoginFilter" )
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        UserServiceImpl userService = new UserServiceImpl();
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            Cookie[] cookies = request.getCookies();
            String uid = null;
            String pwd = null;
            if (cookies != null)
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("cookie_uid")) uid = cookie.getValue();
                    if(cookie.getName().equals("cookie_pwd")) pwd = cookie.getValue();
                }

            if (uid != null && pwd != null){
                try {
                    User u = userService.queryUserById(uid);
                    if(u != null && u.getPassword().equals(pwd))
                        session.setAttribute("user",u);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
