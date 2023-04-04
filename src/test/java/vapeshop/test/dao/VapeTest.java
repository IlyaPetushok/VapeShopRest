package vapeshop.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IVapeDao;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.product.Vape;
import vapeshop.test.config.H2Config;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class})
@Transactional
@WebAppConfiguration
public class VapeTest {

    @Autowired
    private IVapeDao dao;

    @Test
    public void createVapeTest() {
        Vape vape = dao.insertObject(new Vape(120, 22450, "test", new Item(2)));
        assert dao.selectObject(vape.getId()).getType().equals("test") : "problem with vape create";
    }

    @Test
    public void selectVapeTest() {
        Vape vape = dao.insertObject(new Vape(120, 22450, "test", new Item(2)));
        assert dao.selectObject(vape.getId()).getType().equals("test") : "problem with vape select";
    }

    @Test
    public void deleteVapeTest() {
        Vape vape = dao.insertObject(new Vape(120, 22450, "test", new Item(2)));
        assert dao.delete(vape.getId()) : "problem with vape delete";
    }

    @Test
    public void updateVapeTest() {
        Vape vape = dao.update(new Vape(1, 120, 22450, "test update", new Item(2)));
        assert dao.selectObject(vape.getId()).getType().equals("test update") : "problem with vapeUpdate";
    }
}
