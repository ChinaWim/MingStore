package ming.store.biz.admin.service;

import ming.store.entity.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public interface ProductService {
    // 保存商品数据
    void saveProduct(Product product) throws Exception;

    //根据id 查找
    Product queryByid(String id) throws Exception;

    // 查找所有的商品
    List<Product> queryAll() throws Exception;

    //查找所有商品并按时间排序
    List<Product> queryAllByPdate()throws Exception;

    //查找商品 根据名称
    List<Object> queryByName(String name) throws Exception;

    //查找最新商品

    List<Product> queryNewProduct() throws SQLException;

    //查找 热门商品
    List<Product> queryHotProduct() throws SQLException;

    //删除商品
    boolean deleteProductById(String pid) throws Exception;

    //更新
    boolean updateProduct(Product product)throws Exception;
}
