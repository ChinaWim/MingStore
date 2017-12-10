package ming.store.biz.admin.service;

import ming.store.entity.Orders;
import ming.store.entity.PageBean;
import ming.store.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public interface OrdersService {
    // 保存订单
    boolean submitOrders(Orders orders) throws Exception;

    // 更新订单 支付状态为 1
    boolean updateOrdersState(String oid) throws Exception;

    //更新订单
    boolean updateOrders(Orders orders) throws Exception;

    //查找订单 根据id
    Orders queryOrderByid(String oid) throws Exception;

    //查找订单 根据用户id
    List<Orders> queryOrdersByUid(String uid) throws SQLException;

    //查找所有的订单
    List<Orders> queryAllOrders() throws Exception;

    List<Orders> getPageBeanData(User user, int currentPage, int currentCount)throws Exception;

    //获取PageBean
    PageBean<Orders> getPageBean(User user, int currentPage, int currentCount) throws Exception;
}
