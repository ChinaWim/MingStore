package ming.store.biz.admin.dao.impl;

import ming.store.base.BaseDao;
import ming.store.biz.admin.dao.OrderitemDao;
import ming.store.entity.Orderitem;
import ming.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;

/**
 * Created by Ming on 2017/8/23.
 */
public class OrderitemDaoImpl extends BaseDao<Orderitem> implements OrderitemDao {

    @Override
    //保存订单项
    public boolean submitOrderitem(Orderitem orderitem) throws Exception {
        String sql = "insert into orderitem values(?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = JDBCUtils.getConnection();//事务控制
        int len = queryRunner.update(connection,sql,
                orderitem.getItemid(),orderitem.getCount(), orderitem.getSubtotal(),
                orderitem.getProduct().getPid(), orderitem.getOrders().getOid());
        return len >0? true:false;
    }
    @Override
    //更新订单项
    public boolean updateOrderitem(Orderitem orderitem) throws Exception {
        String sql = "update orderitem set count = ? ,subtotal = ? ,pid = ? where itemid = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        int len = queryRunner.update(sql, orderitem.getCount(), orderitem.getSubtotal(), orderitem.getProduct().getPid(),orderitem.getItemid());
        return len >0? true:false;
    }


}
