package vapeshop.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.*;
import project.vapeshop.entity.common.Rating;
import project.vapeshop.entity.product.Category;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.product.Vape;
import project.vapeshop.entity.user.Role;
import project.vapeshop.entity.user.User;
import vapeshop.test.config.H2Config;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class})
@Transactional
@WebAppConfiguration
public class RatingTest {
    @Autowired
    IItemDao daoItem;

    @Autowired
    IVapeDao daoVape;

    @Autowired
    IUserDao daoUser;

    @Autowired
    private IRatingDao dao;

    @Test
    public void createRatingTest(){
        Rating rating=dao.insertObject( new Rating("good", 5, new Item(1), new User(1)));
        assert dao.selectObject(rating.getId()).getComment().equals("good") : "problem with create rating";
    }

    @Test
    public void selectRatingTest(){
        Rating rating=dao.insertObject( new Rating("good", 5, new Item(1), new User(1)));
        assert dao.selectObject(rating.getId()).getComment().equals("good") : "problem with rating";
    }

    @Test
    public void deleteRatingTest(){
        Rating rating=dao.insertObject( new Rating("good", 5, new Item(1), new User(1)));
        assert dao.delete(rating.getId()):"problem with delete rating";
    }

    @Test
    public void updateRatingTest(){
        User user=daoUser.insertObject(new User("Petushok","Ilya","Aleksandrovich","log","pas","a331@mail",new Role(1)));
        Item item=daoItem.insertObject(new Item("photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        daoVape.insertObject(new Vape(120,22450,"test",new Item(item.getId())));
        Rating rating=dao.insertObject(new Rating("good", 5, new Item(item.getId()), new User(user.getId())));
        dao.update(new Rating(rating.getId(),"goodUpdate", 3, new Item(item.getId()), new User(user.getId())));
        assert dao.selectObject(rating.getId()).getComment().equals("goodUpdate") : "problem with update rating";
    }


}
