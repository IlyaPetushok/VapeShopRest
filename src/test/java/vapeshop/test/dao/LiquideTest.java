package vapeshop.test.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IItemDao;
import project.vapeshop.dao.ILiquideDao;
import project.vapeshop.entity.product.Category;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.product.Liquide;
import vapeshop.test.config.H2Config;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class})
@Transactional
@WebAppConfiguration
public class LiquideTest {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ILiquideDao dao;

    @Autowired
    private IItemDao daoItem;

    @Test
    public void createLiquideTest(){
        Item item=daoItem.insertObject(new Item("photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        Liquide liquide=dao.insertObject(new Liquide(new Item(item.getId()),"test", 45, "солевой", 30));
        assert dao.selectObject(liquide.getId()).getFlavour().equals("test") : "проблемы с vaporizer insert";
    }

    @Test
    public void selectLiquideTest(){
        Item item=daoItem.insertObject(new Item("photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        Liquide liquide=dao.insertObject(new Liquide(new Item(item.getId()),"test", 45, "солевой", 30));
        assert dao.selectObject(liquide.getId()).getFlavour().equals("test") : "проблемы с liquide select by id";
    }

    @Test
    public void deleteLiquideTest(){
        Item item=daoItem.insertObject(new Item("photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        Liquide liquide = dao.insertObject(new Liquide(new Item(item.getId()),"test", 45, "солевой", 30));
        assert dao.delete(liquide.getId()):"проблемы с liquide delete";
    }

    @Test
    public void updateLiquideTest(){
        Item item=daoItem.insertObject(new Item("photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        Liquide liquide=dao.insertObject(new Liquide(new Item(item.getId()),"test", 45, "солевой", 30));
        dao.update(new Liquide(liquide.getId(), new Item(item.getId()),"test update", 45, "солевой", 30));
        assert dao.selectObject(liquide.getId()).getFlavour().equals("test update") : "проблемы с liquide update";
    }
}
