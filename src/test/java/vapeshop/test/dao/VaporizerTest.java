package vapeshop.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IItemDao;
import project.vapeshop.dao.IVaporizerDao;
import project.vapeshop.entity.product.Category;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.product.Vaporizer;
import vapeshop.test.config.H2Config;
import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class})
@Transactional
@WebAppConfiguration
public class VaporizerTest {
    @Autowired
    private IVaporizerDao dao;

    @Autowired
    private IItemDao daoItem;

    @Test
    public void createVaporizerTest(){
        Item item=daoItem.insertObject(new Item("photoest", "test product", new Category("Испарители,Картриджы,Койлы"), new BigDecimal(Double.toString(23.0)), 10));
        Vaporizer vaporizer=dao.insertObject(new Vaporizer(9.9,"испаритель",new Item(item.getId())));
        assert dao.selectObject(vaporizer.getId()).getResistance()==9.9 : "problem with vaporizer insert";
    }

    @Test
    public void selectVaporizerTest(){
        Item item=daoItem.insertObject(new Item("photoest", "test product", new Category("Испарители,Картриджы,Койлы"), new BigDecimal(Double.toString(23.0)), 10));
        Vaporizer vaporizer=dao.insertObject(new Vaporizer(9.9,"испаритель",new Item(item.getId())));
        assert dao.selectObject(vaporizer.getId()).getType().equals("испаритель") : "problem with vaporizer select by id";
    }

    @Test
    public void deleteVaporizerTest(){
        Item item=daoItem.insertObject(new Item("photoest", "test product", new Category("Испарители,Картриджы,Койлы"), new BigDecimal(Double.toString(23.0)), 10));
        Vaporizer vaporizer=dao.insertObject(new Vaporizer(9.9,"испаритель",new Item(item.getId())));
        assert dao.delete(vaporizer.getId()):"problem with vaporier delete";
    }

    @Test
    public void updateVaporizerTest(){
        Item item=daoItem.insertObject(new Item("photoest", "test product", new Category("Испарители,Картриджы,Койлы"), new BigDecimal(Double.toString(23.0)), 10));
        Vaporizer vaporizer=dao.insertObject(new Vaporizer(9.9,"испаритель",new Item(item.getId())));
        dao.update(new Vaporizer(vaporizer.getId(), 1.8,"картриджы",new Item(item.getId())));
        assert dao.selectObject(vaporizer.getId()).getType().equals("испаритель") : "problem with vaporizer update";
    }
}
