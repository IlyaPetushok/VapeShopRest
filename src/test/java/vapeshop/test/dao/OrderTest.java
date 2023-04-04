package vapeshop.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IItemDao;
import project.vapeshop.dao.IOrderDao;
import project.vapeshop.entity.common.Order;
import project.vapeshop.entity.common.StatusOrder;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.user.User;
import vapeshop.test.config.H2Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class})
@Transactional
@WebAppConfiguration
public class OrderTest {

    @Autowired
    private IOrderDao dao;

    @Autowired
    private IItemDao daoItem;

    @Test
    public void createOrderTest(){
        List<Item> itemList=new ArrayList<>();
        itemList.add(daoItem.selectObject(1));
        Order order=dao.insertObject(new Order(new Date(2023, Calendar.FEBRUARY,26), new User(1),StatusOrder.Sent,150.0,itemList));
        assert dao.selectObject(order.getId())!=null : "problem without order create";
    }

    @Test
    public void selectOrderTest(){
        List<Item> itemList=new ArrayList<>();
        itemList.add(daoItem.selectObject(1));
        Order order=dao.insertObject(new Order(new Date(2023, Calendar.FEBRUARY,26), new User(1),StatusOrder.Sent,150.0,itemList));
        assert dao.selectObject(order.getId()) !=null : "problem without order select";
    }

    @Test
    public void deleteOrderTest(){
        List<Item> itemList=new ArrayList<>();
        itemList.add(daoItem.selectObject(1));
        Order order =dao.insertObject(new Order(new Date(2023, Calendar.FEBRUARY,26), new User(1),StatusOrder.Sent,150.0,itemList));
        assert dao.delete(order.getId()):"problem without order delete";
    }

    @Test
    public void updateOrderTest(){
        List<Item> itemList=new ArrayList<>();
        itemList.add(daoItem.selectObject(1));
        Order order =dao.insertObject(new Order(new Date(2023, Calendar.FEBRUARY,26), new User(1),StatusOrder.Sent,150.0,itemList));
        dao.update(new Order(order.getId(),new Date(2023, Calendar.FEBRUARY,26), new User(1),StatusOrder.Accepted,150.0,itemList));
        assert dao.selectObject(order.getId()).getStatus().equals(StatusOrder.Accepted) : "problem without order update";
    }
}
