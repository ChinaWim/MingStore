package ming.store.listener; /**
 * Created by Ming on 2017/8/24.
 */

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class LoginListener implements
        HttpSessionListener, HttpSessionAttributeListener {

    public LoginListener() {
    }

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
    }


    public void attributeAdded(HttpSessionBindingEvent sbe) {
        if(sbe.getName().equals("username")){

        }

    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
    }
}
