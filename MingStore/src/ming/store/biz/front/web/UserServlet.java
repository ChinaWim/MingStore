package ming.store.biz.front.web;

import ming.store.base.BaseServlet;
import ming.store.biz.front.service.UserService;
import ming.store.biz.front.service.impl.UserServiceImpl;
import ming.store.entity.User;
import ming.store.utils.CommonUtils;
import ming.store.utils.MD5Utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ming on 2017/8/24.f
 */
@WebServlet(urlPatterns = {"/user"})
public class UserServlet extends BaseServlet {
    UserService userService = new UserServiceImpl();
    //注册业务
    public void register(HttpServletRequest request, HttpServletResponse response){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:SS");
            User user = CommonUtils.copyToBean(request,User.class);
            user.setUid(CommonUtils.getUUID());
            user.setState(0);
            user.setCode(CommonUtils.getUUID64());
            user.setRegistertime(new Date());
            System.out.println(user);
            boolean isRegisterSuccess = false;
            isRegisterSuccess = userService.register(user);
            //判断是否注册成功
            if(isRegisterSuccess){
                //注册成功
                response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
            }else {
                //注册失败
                response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
            }
            //处理异常
        } catch (Exception e) {
            request.setAttribute("msg",e.getMessage());
            e.printStackTrace();
        }

    }
    //激活业务
    public void active(HttpServletRequest request,HttpServletResponse response){
        //激活码
        String code = request.getParameter("code");
        try {
            boolean activeFlag = userService.activeUser(code);
            if(activeFlag){
                //激活成功
                System.out.println("激活成功！");
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }else {
                System.out.println("激活失败！可能你超过了有效期！或服务繁忙请稍后再试试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //登录业务
    public void login(HttpServletRequest request,HttpServletResponse response){

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = MD5Utils.md5(password);
        String autoLogin = request.getParameter("autoLogin");
        String saveUsername = request.getParameter("saveUsername");
        try {
            User user = userService.queryUserByUsername(username);
            if(user != null && user.getState() == 1){
                //用户不为空就判断密码
                if( !user.getPassword().equals(password)){
                    request.setAttribute("msg_password","密码错误");
                    request.getRequestDispatcher("/login.jsp").forward(request,response);
                    return;
                }else{
                    HttpSession session = request.getSession();
                    session.setAttribute("user",user);
                    session.setAttribute("loginTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    //判断是否 记住用户名
                    if(saveUsername != null){
                        Cookie cookie = new Cookie("username",username);
                        cookie.setMaxAge(60*30);
                        response.addCookie(cookie);
                    }else {
                        Cookie cookie = new Cookie("username","");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                    //判断是否自动登录
                    if(autoLogin != null){
                        Cookie cookie_uid = new Cookie("cookie_uid",user.getUid());
                        Cookie cookie_pwd = new Cookie("cookie_pwd",user.getPassword());
                        cookie_uid.setMaxAge(60*15);
                        cookie_pwd.setMaxAge(60*15);
                        response.addCookie(cookie_uid);
                        response.addCookie(cookie_pwd);
                    }
                    response.sendRedirect(request.getContextPath());
                    System.out.println("登录成功！");
                }

            }else {
                request.setAttribute("msg_username","没有此用户");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //退出登录
    public void logout(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(session != null)
        session.invalidate();
        Cookie username = new Cookie("username","");
        username.setMaxAge(0);

        Cookie cookie_uid = new Cookie("cookie_uid","");
        cookie_uid.setMaxAge(0);
        response.addCookie(username);response.addCookie(cookie_uid);

        Cookie cookie_pwd = new Cookie("cookie_pwd","");
        cookie_pwd.setMaxAge(0);
        response.addCookie(username);response.addCookie(cookie_pwd);

        try {
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 检查用户名是否重复 业务
    public void checkUsername(HttpServletRequest request,HttpServletResponse response){
        String username = request.getParameter("username");
        try {
            User user = userService.queryUserByUsername(username);
            if(user != null){
                response.getWriter().write("{\"isExist\":true}");
            }else {
                response.getWriter().write("{\"isExist\":false}");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
