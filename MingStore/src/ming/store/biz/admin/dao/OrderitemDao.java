package ming.store.biz.admin.dao;

import ming.store.entity.Orderitem;

/**
 * Created by Ming on 2017/8/23.
 */
public interface OrderitemDao {
    boolean submitOrderitem(Orderitem orderitem) throws Exception;

    boolean updateOrderitem(Orderitem orderitem) throws Exception;
}
