package project.vapeshop.dao;

import project.vapeshop.dao.Dao;
import project.vapeshop.entity.EntityId;
import project.vapeshop.entity.user.User;
import project.vapeshop.predicate.CustomPredicate;

import java.util.List;

public interface IUserDao extends Dao<User,Integer> {
    User findByLoginAndPassword(User user);
    User findByLogin(String login);
//    List<User> findByFilter(List<CustomPredicate> predicates);
}
