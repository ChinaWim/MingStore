package ming.store.biz.front.dao.impl;

import ming.store.base.BaseDao;
import ming.store.biz.front.dao.OrdersDao;
import ming.store.entity.Orders;
import ming.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Ming on 2017/8/23.
 */
public class OrdersDaoImpl extends BaseDao<Orders> implements OrdersDao {
    @Override
    public boolean submitOrder(Orders orders) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        Connection connection = JDBCUtils.getConnection();//因为要控制事务
        int update = queryRunner.update(connection,sql, orders.getOid(), orders.getOrdertime(), orders.getTotal(), orders.getState(),
                orders.getAddress(), orders.getName(), orders.getTelephone(), orders.getUser().getUid());
        if(update > 0) return true;
        return false;
    }
    //更新
    @Override
    public boolean updateOrder(Orders orders) throws Exception{
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "update  orders set address = ?,name=?,telephone=? where oid = ?";
        int update = queryRunner.update(sql,orders.getAddress(), orders.getName(), orders.getTelephone(),orders.getOid());
        if(update > 0) return true;
        return false;
    }
}
