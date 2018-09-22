package ming.store.biz.admin.service.impl;

import ming.store.biz.admin.dao.impl.ProductDaoImpl;
import ming.store.biz.admin.service.ProductService;
import ming.store.entity.Product;
import ming.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public class ProductServiceImpl implements ProductService {

    public ProductDaoImpl productDao = new ProductDaoImpl();
    // 保存商品数据
    @Override
    public void saveProduct(Product product) throws Exception {
        productDao.save(product);
    }
    @Override
    //根据id 查找
    public Product queryByid(String id) throws Exception {
        return productDao.findById(id);
    }
    @Override
    // 查找所有的商品
    public List<Product> queryAll() throws Exception {
        return productDao.findAll();
    }
    @Override
    //查找所有商品并按时间排序
    public List<Product> queryAllByPdate()throws Exception{
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from product Order by pdate desc";
        List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
        return list;
    }

    @Override
    //查找商品 根据名称
    public List<Object> queryByName(String name) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from product where pname like ?";
        List<Object> list = queryRunner.query(sql, new ColumnListHandler("pname"), new Object[]{"%"+name+"%"});
        return  list;
    }

    @Override
    //查找 热门商品
    public List<Product> queryHotProduct() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from product where is_hot = 1 limit 0,9";
        List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
        return list;
    }
    @Override
    //查找最新商品
    public List<Product> queryNewProduct() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM product  ORDER BY pdate DESC LIMIT 0,9";
        List<Product> list = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
        return list;
    }
    @Override
    //删除商品
    public boolean deleteProductById(String pid) throws Exception {
        boolean b = productDao.deleteById(pid);
        return b;
    }
    @Override
    //更新
    public boolean updateProduct(Product product)throws Exception {
        boolean update = productDao.update(product);
        return update;
    }


}
