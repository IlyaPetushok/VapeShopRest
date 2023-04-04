package project.vapeshop.dao.impl;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.vapeshop.dao.IOrderDao;
import project.vapeshop.entity.common.Order;
import project.vapeshop.entity.common.Order_;
import project.vapeshop.entity.common.StatusOrder;
import project.vapeshop.entity.user.User;
import project.vapeshop.entity.user.User_;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao<Order, Integer> implements IOrderDao {

    @Override
    public Order selectObject(Integer id) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery= criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot=criteriaQuery.from(Order.class);
        orderRoot.fetch(Order_.items, JoinType.LEFT);
        orderRoot.fetch(Order_.user,JoinType.LEFT);
        criteriaQuery.select(orderRoot).where(criteriaBuilder.equal(orderRoot.get(Order_.id),id)).distinct(true);
        TypedQuery<Order> typedQuery=entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public Order update(Order order) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaUpdate<Order> criteriaUpdate=criteriaBuilder.createCriteriaUpdate(Order.class);
        Root<Order> orderRoot=criteriaUpdate.from(Order.class);
        criteriaUpdate.where(criteriaBuilder.equal(orderRoot.get(Order_.id),order.getId()));
        criteriaUpdate.set(Order_.status,order.getStatus());
        criteriaUpdate.set(Order_.date,order.getDate());
        criteriaUpdate.set(Order_.user,order.getUser());
        criteriaUpdate.set(Order_.price,order.getPrice());
        Query query= entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();
        return entityManager.find(Order.class,order.getId());
    }

    @Override
    public List<Order> selectOrderFindByStatus(StatusOrder statusOrder) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery=criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot=criteriaQuery.from(Order.class);
        criteriaQuery.where(criteriaBuilder.equal(orderRoot.get(Order_.status),statusOrder));
        TypedQuery<Order> typedQuery=entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> selectOrderFindByUser(User user) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery=criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot=criteriaQuery.from(Order.class);
        Join<Order,User> userJoin=orderRoot.join(Order_.user,JoinType.INNER);
        criteriaQuery.select(orderRoot).where(criteriaBuilder.equal(userJoin.get(User_.id),user.getId()));
        TypedQuery<Order> typedQuery=entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
