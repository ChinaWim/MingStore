package ming.stop.base;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Ming on 2017/8/22.
 */
@WebServlet(name = "BaseServlet",urlPatterns = {"/base"})
public class BaseServlet extends HttpServlet {
    protected synchronized  void   doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // action名就是方法名b
        String action = request.getParameter("action");
        if(action != null && !action.equals("")) {
            Class aClass = this.getClass();
            //反射执行这个方法
            try {
                Method method = aClass.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
                method.invoke(this, request, response);
            } catch (Exception e) {
                System.out.println("没有这个方法: "+action);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

    }

    protected  synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
