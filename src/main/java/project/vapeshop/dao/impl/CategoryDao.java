package project.vapeshop.dao.impl;

import org.springframework.stereotype.Repository;
import project.vapeshop.dao.ICategoryDao;
import project.vapeshop.entity.product.Category;

//import javax.persistence.Query;
//import java.util.List;

@Repository
public class CategoryDao extends AbstractDao<Category,Integer>  implements ICategoryDao {

    public static final String SELECT_CATEGORY_ALL = "SELECT cat FROM Category as cat";


//    @Override
//    public List<Category> selectObjects() {
//        Query query= entityManager.createQuery(SELECT_CATEGORY_ALL);
//        return query.getResultList();
//    }
//
//    @Override
//    public Category selectObject(Integer id) {
//        return entityManager.find(Category.class,id);
//    }

}
