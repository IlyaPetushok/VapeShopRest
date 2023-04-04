package project.vapeshop.dao;

import project.vapeshop.entity.common.Order;
import project.vapeshop.entity.common.StatusOrder;
import project.vapeshop.entity.user.User;

import java.util.List;

public interface IOrderDao extends Dao<Order,Integer>{
    List<Order> selectOrderFindByStatus(StatusOrder statusOrder);
    List<Order> selectOrderFindByUser(User user);
}
