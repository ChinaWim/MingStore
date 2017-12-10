package ming.store.biz.front.service.impl;

import ming.store.biz.front.dao.impl.OrderitemDaoImpl;
import ming.store.biz.front.dao.impl.ProductDaoImpl;
import ming.store.biz.front.service.OrderitemService;
import ming.store.entity.Orderitem;
import ming.store.entity.Orders;
import ming.store.utils.JDBCUtils;
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
    //删除订单项
    public boolean deleteOrderitem(String oid) throws Exception {
        return orderitemDao.deleteOrderitem(oid);
    }
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
    //查找Orderitem  OrderitemBean 全部封装
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
