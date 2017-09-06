package ming.stop.biz.admin.service;

import ming.stop.entity.Orderitem;
import ming.stop.entity.Orders;

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
