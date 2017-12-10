package ming.store.biz.admin.service.impl;

import ming.store.biz.admin.service.CategoryService;
import ming.store.biz.front.dao.impl.CategoryDaoImpl;
import ming.store.entity.Category;

import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public class CategoryServiceImpl implements CategoryService {

    CategoryDaoImpl categoryDao = new CategoryDaoImpl();

    @Override
    public boolean deleteCategoryById(String cid) throws Exception {
        boolean b = categoryDao.deleteById(cid);
        return b;
    }

    @Override
    public List<Category> queryAll() throws Exception {
        List<Category> list = categoryDao.findAll();
        return list;
    }
    @Override
    public Category queryById(String cid)throws Exception{
        Category category = categoryDao.findById(cid);
        return category;
    }
    @Override
    public boolean addCategory(Category c) throws Exception {
        return  categoryDao.save(c);
    }
    @Override
    public  boolean updateCategory(Category c) throws Exception {
        return  categoryDao.update( c);
    }

}
