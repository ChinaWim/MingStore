package ming.store.filter;

import java.io.IOException;

/**
 * Created by Ming on 2017/8/22.
 */
@javax.servlet.annotation.WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements javax.servlet.Filter {
    public void destroy() {

    }
    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        chain.doFilter(req, resp);
    }

    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {

    }

}
