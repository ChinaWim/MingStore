package ming.store.biz.front.service.impl;

import ming.store.biz.front.dao.impl.ProductDaoImpl;
import ming.store.biz.front.service.ProductService;
import ming.store.entity.PageBean;
import ming.store.entity.Product;
import ming.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
    //查找商品 根据名称，模糊查询
    public List<Object> queryByName(String name) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from product where pname like ?";
        List<Object> list = queryRunner.query(sql, new ColumnListHandler("pname"), new Object[]{"%"+name+"%"});
        return  list;
    }
    @Override
    //查找商品 根据名称
    public Product  queryByNameToProduct(String name) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from product where pname = ?";
        Product query = queryRunner.query(sql, new BeanHandler<Product>(Product.class), name);
        return query;
    }
    @Override
    //查找  PageBean
    public PageBean<Product> queryPageBeanByCid(String cid, int currentPage, int currentCount) throws Exception {
        PageBean<Product> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setCurrentCount(currentCount);
        //查找总页数
        pageBean.setTotalCount(productDao.getCountByCid(cid));
        pageBean.setTotalPage( (int) ( Math.ceil(pageBean.getTotalCount()*1.0/currentCount) ));
        //封装数据
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from product where cid = ? limit ?,?";
        //设置索引页
        int indexPage = (currentPage-1)*currentCount;
        List<Product> data = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid, indexPage, currentCount);
        pageBean.setData(data);
        return pageBean;
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

    public static void main(String[] args) throws Exception {

    }

}
