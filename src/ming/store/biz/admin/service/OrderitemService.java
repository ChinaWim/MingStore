package ming.store.biz.admin.service;

import ming.store.entity.Orderitem;
import ming.store.entity.Orders;

import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public interface OrderitemService {


    boolean submitOrderitem(Orderitem orderitem) throws Exception;

    boolean updateOrderitem(Orderitem orderitem) throws Exception;

    Orderitem queryOrderitemByid(String id) throws Exception;

    List<Orderitem> queryOrderitemByOrder(Orders order)throws Exception;
}
