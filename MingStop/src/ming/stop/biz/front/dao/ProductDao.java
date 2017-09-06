package ming.stop.biz.front.dao;

import java.sql.SQLException;

/**
 * Created by Ming on 2017/8/23.
 */
public interface ProductDao  {
    //查找 product 总数目 根据cid
    int getCountByCid(String cid) throws SQLException;
}
