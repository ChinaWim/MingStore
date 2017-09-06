package ming.stop.biz.admin.dao.impl;

import ming.stop.base.BaseDao;
import ming.stop.biz.admin.dao.ProductDao;
import ming.stop.entity.Product;
import ming.stop.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * Created by Ming on 2017/8/23.
 */
public class ProductDaoImpl extends BaseDao<Product> implements ProductDao {
        //查找 product 总数目 根据cid
    @Override
    public int getCountByCid(String cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select count(*) from product where cid = ?";
        Long query = (Long)queryRunner.query(sql, new ScalarHandler(), cid);
        return  query.intValue();
    }
}
