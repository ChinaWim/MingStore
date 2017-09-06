package ming.stop.biz.front.dao;

import ming.stop.entity.Orders;

import java.sql.SQLException;

/**
 * Created by Ming on 2017/8/23.
 */
public interface OrdersDao {
    boolean submitOrder(Orders orders) throws SQLException;

    //更新
    boolean updateOrder(Orders orders) throws Exception;
}
