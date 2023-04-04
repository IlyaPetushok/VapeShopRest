package project.vapeshop.dao.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import project.vapeshop.dao.IUserDao;
import project.vapeshop.entity.user.Role;
import project.vapeshop.entity.user.User;
import project.vapeshop.entity.user.User_;

//import javax.persistence.EntityGraph;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class UserDao extends AbstractDao<User, Integer> implements IUserDao {
    @Override
    public User insertObject(User user) {
        Role role=entityManager.find(Role.class,user.getRole().getId());
        user.setRole(role);
        return super.insertObject(user);
    }

//    @Override
//    public List<User> selectObjects() {
//        Query query= entityManager.createQuery("SELECT us from User as us");
//        return query.getResultList();
//    }

    @Override
    public User selectObject(Integer id) {
        EntityGraph<?> entityGraph= entityManager.getEntityGraph("entity-user-graph-role");
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery=criteriaBuilder.createQuery(User.class);
        Root<User> userRoot=criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get(User_.id),id));
        TypedQuery<User> query=entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.fetchgraph",entityGraph);
        return  query.getSingleResult();
    }

    @Override
    public User update(User user) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaUpdate<User> criteriaQuery= criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> userRoot=criteriaQuery.from(User.class);
        criteriaQuery.set(User_.name,user.getName());
        criteriaQuery.set(User_.surname,user.getSurname());
        criteriaQuery.set(User_.patronymic,user.getPatronymic());
        criteriaQuery.set(User_.mail,user.getMail());
        Query query=entityManager.createQuery(criteriaQuery);
        query.executeUpdate();
        return entityManager.find(User.class,user.getId());
    }

    @Override
    public User findByLoginAndPassword(User user) {
        EntityGraph<?> entityGraph= entityManager.getEntityGraph("entity-user-graph-role");
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery=criteriaBuilder.createQuery(User.class);
        Root<User> userRoot=criteriaQuery.from(User.class);
        Predicate predicateLogin=criteriaBuilder.equal(userRoot.get(User_.login),user.getLogin());
        Predicate predicatePassword=criteriaBuilder.equal(userRoot.get(User_.password),user.getPassword());
        criteriaQuery.where(criteriaBuilder.and(predicateLogin,predicatePassword));
        TypedQuery<User> query=entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.loadgraph",entityGraph);
        return query.getSingleResult();
    }

    @Override
    public User findByLogin(String login) {
        EntityGraph<?> entityGraph= entityManager.getEntityGraph("entity-user-graph-role");
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery=criteriaBuilder.createQuery(User.class);
        Root<User> userRoot=criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get(User_.login),login));
        TypedQuery<User> query=entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.loadgraph",entityGraph);
        User user=query.getSingleResult();
        System.out.println(user.getRole().getPrivileges().size());
        user.getRole().setPrivileges(user.getRole().getPrivileges());
        return query.getSingleResult();
    }

}
