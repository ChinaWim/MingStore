package ming.stop.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Ming on 2017/8/30.
 */
@WebFilter(filterName = "UserFilter")
public class UserFilter implements Filter {
    public void destroy() {

    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if(user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
            chain.doFilter(request,response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
