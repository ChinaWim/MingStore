package ming.store.biz.admin.service.impl;

import ming.store.biz.admin.service.OrderitemService;
import ming.store.biz.admin.service.OrdersService;
import ming.store.biz.front.dao.impl.OrdersDaoImpl;
import ming.store.entity.Orderitem;
import ming.store.entity.Orders;
import ming.store.entity.PageBean;
import ming.store.entity.User;
import ming.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public class OrdersServiceImpl implements OrdersService {
    OrdersDaoImpl ordersDao = new OrdersDaoImpl();
    OrderitemService orderitemService = new OrderitemServiceImpl();
    // 提交订单
    @Override
    public  boolean submitOrders(Orders orders) {
        boolean b = false;
        try {
            // 开启事务
            JDBCUtils.startTransaction();
            // 提交订单
            b = ordersDao.submitOrder(orders);
            //提交订单项
            List<Orderitem> orderitems = orders.getOrderitems();
            for (Orderitem orderitem : orderitems) {
                b = orderitemService.submitOrderitem(orderitem);
            }

        }catch(Exception e){//遇到异常要回滚
            e.printStackTrace();
            try {
                JDBCUtils.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                JDBCUtils.commitAndRelease();//最终提交
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;
    }
    // 更新订单 支付状态为 1
    @Override
    public boolean updateOrdersState(String oid) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "update orders set state = 1 where oid = ?";
        int update = queryRunner.update(sql, oid);
        return update >0 ? true:false;
    }


    //更新订单
    @Override
    public boolean updateOrders(Orders orders) throws Exception {
        boolean b = ordersDao.updateOrder(orders);
        return b;
    }
    //查找订单 根据id
    @Override
    public Orders queryOrderByid(String oid) throws Exception {
        Orders order = ordersDao.findById(oid);
        return order;
    }
    //查找订单 根据用户id
    @Override
    public List<Orders> queryOrdersByUid(String uid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from orders where uid = ?";
        List<Orders> list = queryRunner.query(sql, new BeanListHandler<Orders>(Orders.class), uid);
        return list;
    }
    //查找订单数量 根据用户id
    public int queryCountByUid(String uid)throws Exception{
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select count(*) from orders where uid = ?";
        Long len= (Long)queryRunner.query(sql, new ScalarHandler(), uid);
        return len.intValue();
    }

    //查找所有的订单
    @Override
    public List<Orders> queryAllOrders() throws Exception {
        List<Orders> list = ordersDao.findAll();
        return list;
    }
    @Override
    //获取 PageBean 的DATA
    public List<Orders> getPageBeanData(User user,int currentPage,int currentCount)throws Exception{
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from orders where uid = ? order by ordertime desc limit ?,?";
        int index = (currentPage-1)*currentCount;
        List<Orders> query = queryRunner.query(sql, new BeanListHandler<Orders>(Orders.class), user.getUid(), index, currentCount);
        for (Orders orders : query) {
            orders.setUser(user);
            //根据 oid
            List<Orderitem> orderitems = orderitemService.queryOrderitemByOrder(orders);
            orders.setOrderitems(orderitems);
        }
        return query;
    }

    @Override
    //获取PageBean
    public PageBean<Orders> getPageBean(User user, int currentPage, int currentCount) throws Exception {
        PageBean<Orders> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setCurrentCount(currentCount);
        pageBean.setTotalCount(queryCountByUid(user.getUid()));
        pageBean.setTotalPage(   (int) (Math.ceil( (pageBean.getTotalCount()*1.0 / currentCount )) ) );
        pageBean.setData(  getPageBeanData (user,currentPage,currentCount));
        return  pageBean;
    }



}
