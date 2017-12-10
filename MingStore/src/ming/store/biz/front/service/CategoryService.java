package ming.store.biz.front.service;

import ming.store.entity.Category;

import java.util.List;

/**
 * Created by Ming on 2017/8/23.
 */
public interface CategoryService {

    boolean addCategory(Category c) throws Exception;

    List<Category> queryAll() throws Exception;

    boolean updateCategory(Category c) throws Exception;
}
