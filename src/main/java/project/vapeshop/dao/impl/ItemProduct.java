package project.vapeshop.dao.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import project.vapeshop.dao.IItemDao;
import project.vapeshop.entity.product.*;

//import javax.persistence.EntityGraph;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.*;
import java.util.List;


@Repository
public class ItemProduct extends AbstractDao<Item,Integer> implements IItemDao {
    private static final String LIQUIDE ="Жидкости";
    private static final String VAPORIZER ="Испарители,Картриджы,Койлы";
    private static final String VAPE ="Вейпы и подики";

    @Override
    public Item insertObject(Item item) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery=criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot= criteriaQuery.from(Category.class);
        criteriaQuery.where(criteriaBuilder.equal(categoryRoot.get(Category_.name),item.getCategory().getName()));
        TypedQuery<Category> query=entityManager.createQuery(criteriaQuery);
        item.setCategory(query.getSingleResult());
        return super.insertObject(item);
    }

//    @Override
//    public List<Item> selectObjects() {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Item> criteriaQuery=criteriaBuilder.createQuery(Item.class);
//        Root<Item> itemRoot=criteriaQuery.from(Item.class);
//        TypedQuery<Item> typedQuery= entityManager.createQuery(criteriaQuery);
//        return typedQuery.getResultList();
//    }

    public Item itemGetWithProductCategory(Item item){
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery= criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot=criteriaQuery.from(Item.class);
        switch (item.getCategory().getName()){
            case LIQUIDE:
                itemRoot.join(Item_.liquide,JoinType.INNER);
                break;
            case VAPORIZER:
                itemRoot.join(Item_.vaporizer,JoinType.INNER);
                break;
            case VAPE:
                itemRoot.join(Item_.vape,JoinType.INNER);
                break;
        }
        criteriaQuery.where(criteriaBuilder.equal(itemRoot.get(Item_.id),item.getId()));
        TypedQuery<Item> typedQuery=entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public Item selectObject(Integer id) {
        EntityGraph<?> entityGraph=entityManager.getEntityGraph("entity-item-graph-category");
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery= criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot= criteriaQuery.from(Item.class);
        criteriaQuery.where(criteriaBuilder.equal(itemRoot.get(Item_.id),id));
        TypedQuery<Item> typedQuery= entityManager.createQuery(criteriaQuery);
        typedQuery.setHint("javax.persistence.loadgraph",entityGraph);
        return itemGetWithProductCategory(typedQuery.getSingleResult());
    }

    @Override
    public Item update(Item item) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaUpdate<Item> criteriaQuery= criteriaBuilder.createCriteriaUpdate(Item.class);
        Root<Item> itemRoot= criteriaQuery.from(Item.class);
        criteriaQuery.where(criteriaBuilder.equal(itemRoot.get(Item_.id),item.getId()));
        criteriaQuery.set(Item_.name,item.getName());
        criteriaQuery.set(Item_.photo,item.getPhoto());
        criteriaQuery.set(Item_.quantity,item.getQuantity());
        criteriaQuery.set(Item_.price,item.getPrice());
        Query query= entityManager.createQuery(criteriaQuery);
        query.executeUpdate();
        return selectObject(item.getId());
    }

    @Override
    public boolean delete(Integer id) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> itemCriteriaDelete= criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot= itemCriteriaDelete.from(Item.class);
        itemCriteriaDelete.where(criteriaBuilder.equal(itemRoot.get(Item_.id),id));
        Query query=entityManager.createQuery(itemCriteriaDelete);
        entityManager.remove(query.getSingleResult());
        return true;
    }

    @Override
    public List<Item> selectFindByCategory(String nameCategory) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery= criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot=criteriaQuery.from(Item.class);
        Join<Item,Category> categoryItemJoin=itemRoot.join(Item_.category);
        criteriaQuery.where(criteriaBuilder.equal(categoryItemJoin.get(Category_.name),VAPORIZER));
        TypedQuery<Item> query=entityManager.createQuery(criteriaQuery);
        List<Item> items=query.getResultList();
        return query.getResultList();
    }
}
