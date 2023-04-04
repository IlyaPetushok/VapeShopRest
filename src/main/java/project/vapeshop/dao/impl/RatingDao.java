package project.vapeshop.dao.impl;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IRatingDao;
import project.vapeshop.entity.common.Rating;
import project.vapeshop.entity.common.Rating_;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.user.User;

//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class RatingDao extends AbstractDao<Rating, Integer> implements IRatingDao {

    @Override
    public Rating insertObject(Rating rating) {
        User user = entityManager.find(User.class, rating.getUser().getId());
        rating.setUser(user);
        Item item = entityManager.find(Item.class, rating.getItem().getId());
        rating.setItem(item);
        return super.insertObject(rating);
    }

//    @Override
//    public List<Rating> selectObjects() {
//        Query query = entityManager.createQuery("select rat from Rating as rat");
//        return query.getResultList();
//    }

    @Override
    public Rating selectObject(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rating> criteriaQuery = criteriaBuilder.createQuery(Rating.class);
        Root<Rating> ratingRoot = criteriaQuery.from(Rating.class);
        criteriaQuery.where(criteriaBuilder.equal(ratingRoot.get(Rating_.id), id));
        TypedQuery<Rating> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public Rating update(Rating rating) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaUpdate<Rating> criteriaQuery=criteriaBuilder.createCriteriaUpdate(Rating.class);
        Root<Rating> ratingRoot=criteriaQuery.from(Rating.class);
        criteriaQuery.where(criteriaBuilder.equal(ratingRoot.get(Rating_.id),rating.getId()));
        criteriaQuery.set(Rating_.comment,rating.getComment());
        criteriaQuery.set(Rating_.quantityStar,rating.getQuantityStar());
        Query query=entityManager.createQuery(criteriaQuery);
        query.executeUpdate();
        return entityManager.find(Rating.class,rating.getId());
    }

    @Override
    public boolean delete(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Rating> criteriaDelete = criteriaBuilder.createCriteriaDelete(Rating.class);
        Root<Rating> ratingRoot = criteriaDelete.from(Rating.class);
        criteriaDelete.where(criteriaBuilder.equal(ratingRoot.get(Rating_.id), id));
        Query query = entityManager.createQuery(criteriaDelete);
        query.executeUpdate();
        return true;
    }
}
