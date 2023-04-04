package project.vapeshop.dao.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IVapeDao;
import project.vapeshop.entity.product.*;

//import javax.persistence.EntityGraph;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.CriteriaUpdate;
//import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class VapeDao extends AbstractDao<Vape,Integer> implements IVapeDao {

    public static final String SELECT_VAPE = "select vape from Vape as vape";

    @Override
    public Vape insertObject(Vape vape) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaQuery=criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot=criteriaQuery.from(Item.class);
        criteriaQuery.where(criteriaBuilder.equal(itemRoot.get(Item_.id),vape.getItemForVape().getId()));
        Query query= entityManager.createQuery(criteriaQuery);
        Item item= (Item) query.getSingleResult();
        vape.setItemForVape(item);
        return super.insertObject(vape);
    }

//    @Override
//    public List<Vape> selectObjects() {
//        Query query=entityManager.createQuery(SELECT_VAPE);
//        return query.getResultList();
//    }

    @Override
    public Vape selectObject(Integer id) {
        EntityGraph<?> entityGraph= entityManager.getEntityGraph("entity-graph-item");
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Vape> criteriaQuery= criteriaBuilder.createQuery(Vape.class);
        Root<Vape> vapeRoot= criteriaQuery.from(Vape.class);
        criteriaQuery.select(vapeRoot)
                .where(criteriaBuilder.equal(vapeRoot.get(Vape_.id),id));
        TypedQuery<Vape> query= entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.loadgraph",entityGraph);
        return query.getSingleResult();
    }

    @Override
    public Vape update(Vape vape) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaUpdate<Vape> criteriaUpdate= criteriaBuilder.createCriteriaUpdate(Vape.class);
        Root<Vape> vapeRoot= criteriaUpdate.from(Vape.class);
        criteriaUpdate.set(Vape_.type,vape.getType());
        criteriaUpdate.set(Vape_.itemForVape,vape.getItemForVape());
        criteriaUpdate.set(Vape_.battery,vape.getBattery());
        criteriaUpdate.set(Vape_.power,vape.getPower());
        Query query=entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();
        return entityManager.find(Vape.class,vape.getId());
    }

    @Override
    public List<Vape> findByTypeVape(String typeVape) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Vape> criteriaQuery= criteriaBuilder.createQuery(Vape.class);
        Root<Vape> vapeRoot= criteriaQuery.from(Vape.class);
        criteriaQuery.where(criteriaBuilder.equal(vapeRoot.get(Vape_.type),typeVape));
        TypedQuery<Vape> query= entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
