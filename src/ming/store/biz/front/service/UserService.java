package ming.store.biz.front.service;

import ming.store.entity.User;

import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public interface UserService {
     boolean register(User user) throws Exception;
     boolean updateUser(User user) throws Exception;
     User queryUserById(String id) throws Exception;
     User queryUserByUsername(String username) throws Exception;
     List<User> queryAllUser() throws Exception;
     boolean activeUser(String code) throws Exception;
}
