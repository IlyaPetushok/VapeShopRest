package project.vapeshop.dao.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import project.vapeshop.dao.ILiquideDao;
import project.vapeshop.entity.product.*;
//import javax.persistence.EntityGraph;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class LiquideDao extends AbstractDao<Liquide,Integer> implements ILiquideDao {
    @Override
    public Liquide insertObject(Liquide liquide) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery=criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot=criteriaQuery.from(Item.class);
        criteriaQuery.where(criteriaBuilder.equal(itemRoot.get(Item_.id),liquide.getItemForLiquide().getId()));
        Query query= entityManager.createQuery(criteriaQuery);
        Item item= (Item) query.getSingleResult();
        liquide.setItemForLiquide(item);
        return super.insertObject(liquide);
    }

//    @Override
//    public List<Liquide> selectObjects() {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Liquide> criteriaQuery=criteriaBuilder.createQuery(Liquide.class);
//        Root<Liquide> liquideRoot=criteriaQuery.from(Liquide.class);
//        TypedQuery<Liquide> query= entityManager.createQuery(criteriaQuery);
//        return query.getResultList();
//    }

    @Override
    public  Liquide selectObject(Integer id) {
        EntityGraph<?> entityGraph= entityManager.getEntityGraph("liquide-with-item");
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Liquide> criteriaQuery= criteriaBuilder.createQuery(Liquide.class);
        Root<Liquide> liquideRoot= criteriaQuery.from(Liquide.class);
        criteriaQuery.select(liquideRoot)
                .where(criteriaBuilder.equal(liquideRoot.get(Liquide_.id),id));
        TypedQuery<Liquide> query= entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.loadgraph",entityGraph);
        Liquide liquide=query.getSingleResult();
        return query.getSingleResult();
    }

    @Override
    public Liquide update(Liquide liquide) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaUpdate<Liquide> criteriaUpdate=criteriaBuilder.createCriteriaUpdate(Liquide.class);
        Root<Liquide> liquideRoot=criteriaUpdate.from(Liquide.class);
        criteriaUpdate.where(criteriaBuilder.equal(liquideRoot.get(Liquide_.id),liquide.getId()));
        criteriaUpdate.set(Liquide_.typeNicotine, liquide.getTypeNicotine());
        criteriaUpdate.set(Liquide_.fortress,liquide.getFortress());
        criteriaUpdate.set(Liquide_.flavour, liquide.getFlavour());
        criteriaUpdate.set(Liquide_.volume,liquide.getVolume());
        Query query=entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();
        return liquide;
    }

    @Override
    public boolean delete(Integer id) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaDelete<Liquide> criteriaDelete =criteriaBuilder.createCriteriaDelete(Liquide.class);
        Root<Liquide> liquideRoot=criteriaDelete.from(Liquide.class);
        criteriaDelete.where(criteriaBuilder.equal(liquideRoot.get(Liquide_.id),id));
        Query query=entityManager.createQuery(criteriaDelete);
        query.executeUpdate();
        return true;
    }

    @Override
    public List<Liquide> findByTypeNicotine(String typeNicotineStr) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Liquide> criteriaQuery = criteriaBuilder.createQuery(Liquide.class);
        Root<Liquide> liquideRoot=criteriaQuery.from(Liquide.class);
        criteriaQuery.where(criteriaBuilder.equal(liquideRoot.get(Liquide_.typeNicotine),typeNicotineStr));
        TypedQuery<Liquide> liquideTypedQuery=entityManager.createQuery(criteriaQuery);
        return liquideTypedQuery.getResultList();
    }
}
