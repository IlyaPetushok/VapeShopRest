package project.vapeshop.dao;

import project.vapeshop.entity.product.Item;

//import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface IItemDao extends Dao<Item, Integer> {
    List<Item> selectFindByCategory(String nameCategory);
}
