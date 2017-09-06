package ming.stop.biz.admin.service;

import ming.stop.entity.Category;

import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public interface CategoryService {

    Category queryById(String cid)throws Exception;

    boolean addCategory(Category c) throws Exception;

    boolean deleteCategoryById(String cid) throws Exception;

    List<Category> queryAll() throws Exception;

    boolean updateCategory(Category c) throws Exception;
}
