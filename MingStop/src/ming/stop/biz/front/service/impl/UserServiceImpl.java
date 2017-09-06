package ming.stop.biz.front.service.impl;

import ming.stop.biz.front.dao.impl.UserDaoImpl;
import ming.stop.biz.front.service.UserService;
import ming.stop.entity.User;
import ming.stop.utils.JDBCUtils;
import ming.stop.utils.MD5Utils;
import ming.stop.utils.MailUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.util.Date;
import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public class UserServiceImpl implements UserService {
    UserDaoImpl userDao = new UserDaoImpl();
    //用户注册
    @Override
    public boolean register(User user) throws Exception {
        // 用户密码通过 md5加密
        user.setPassword(MD5Utils.md5(user.getPassword()));
        if(userDao.save(user)){
            //发送激活邮件
                new MailUtils(user).run();
            return true;
        }else {
            return false;
        }

    }
    //激活用户
    @Override
    public boolean activeUser(String code) throws Exception {
        boolean activeFlag = false;
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from user where code = ?";
        User user = queryRunner.query(sql, new BeanHandler<User>(User.class), new Object[]{code});
        if(user != null){
            //再次激活 直接返回true
            if(user.getState() == 1)return true;
            double afterTime = (new Date().getTime()-user.getRegistertime().getTime())*1.0/1000/60/60;
            System.out.println(afterTime);
            //判断是否超过 48天
            if(afterTime <= 48) {
                user.setState(1);
                if (updateUser(user)) activeFlag = true;
            }
        }
        return activeFlag;
    }

    //更新用户操作
    @Override
    public boolean updateUser(User user) throws Exception {
        return userDao.update(user);
    }
    //查找用户 根据id
    @Override
    public User queryUserById(String id) throws Exception {
        return userDao.findById(id);
    }
    //查找用户 根据名称
    @Override
    public User queryUserByUsername(String username) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from user where username = ?";
        User user = queryRunner.query(sql, new BeanHandler<User>(User.class), new Object[]{username});
        return user;
    }
    //查找所有用户
    @Override
    public List<User> queryAllUser() throws Exception {
        List<User> list = userDao.findAll();
        return list;
    }

}
