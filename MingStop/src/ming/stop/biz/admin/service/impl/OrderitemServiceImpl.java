package ming.stop.biz.admin.service.impl;

import ming.stop.biz.admin.service.OrderitemService;
import ming.stop.biz.front.dao.impl.OrderitemDaoImpl;
import ming.stop.biz.front.dao.impl.ProductDaoImpl;
import ming.stop.entity.Orderitem;
import ming.stop.entity.Orders;
import ming.stop.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public class OrderitemServiceImpl implements OrderitemService {
    OrderitemDaoImpl orderitemDao = new OrderitemDaoImpl();
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    //保存订单项
    public boolean submitOrderitem(Orderitem orderitem) throws Exception {
        boolean b = orderitemDao.submitOrderitem(orderitem);
        return b;
    }
    @Override
    public boolean updateOrderitem(Orderitem orderitem) throws Exception {
        boolean b = orderitemDao.updateOrderitem(orderitem);
        return b;
    }

    @Override
    public Orderitem queryOrderitemByid(String id) throws Exception {
        Orderitem byId = orderitemDao.findById(id);
        return byId;
    }
    public List<Orderitem> queryOrderitemByOrder(Orders order)throws Exception{
        QueryRunner queryRunner= new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from orderitem where oid = ?";
        List<Orderitem> list = queryRunner.query(sql, new BeanListHandler<Orderitem>(Orderitem.class), order.getOid());
        for (Orderitem orderitem : list) {
            orderitem.setOrders(order);
            orderitem.setProduct( productDao.findById(orderitem.getPid()));
        }
        return list;
    }

}
