package vapeshop.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.config.SpringApplicationConfig;
import project.vapeshop.dao.IItemDao;
import project.vapeshop.dao.IVapeDao;
import project.vapeshop.entity.product.Category;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.product.Vape;
import vapeshop.test.config.H2Config;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class})
@Transactional
@WebAppConfiguration
public class ItemTest {
    @Autowired
    IItemDao dao;

    @Autowired
    IVapeDao daoVape;

    @Test
    public void createItemTest(){
        Item item=dao.insertObject(new Item("photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        daoVape.insertObject(new Vape(120,22450,"test",new Item(item.getId())));
        assert item.getName().equals("HotSpot BubleGum") : "dont work create item";
    }

    @Test
    public void selectItemTest(){
        Item item=dao.insertObject(new Item("photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        daoVape.insertObject(new Vape(120,22450,"test",new Item(item.getId())));
        assert dao.selectObject(item.getId()).getName().equals("HotSpot BubleGum") : "проблемы с item";
    }

    @Test
    public void deleteItemTest(){
        Item item=dao.insertObject(new Item("photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        daoVape.insertObject(new Vape(120,22450,"test",new Item(item.getId())));
        assert dao.delete(item.getId()):"problem with item";
    }

    @Test
    public void updateItemTest(){
        Item item=dao.insertObject(new Item("photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        daoVape.insertObject(new Vape(120,22450,"test",new Item(item.getId())));
        dao.update(new Item(item.getId(),"photo4", "HotSpot BubleGum", new Category("Вейпы и подики"), new BigDecimal(Double.toString(23.0)), 15));
        assert dao.selectObject(item.getId()).getName().equals("HotSpot BubleGum") : "problem with item daoItemUpdate";
    }
}
