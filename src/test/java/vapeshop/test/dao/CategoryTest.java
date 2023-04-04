package vapeshop.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.Dao;
import project.vapeshop.dao.ICategoryDao;
import project.vapeshop.entity.product.Category;
import vapeshop.test.config.H2Config;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class})
@Transactional
@WebAppConfiguration
public class CategoryTest {
    @Autowired
    ICategoryDao dao;

    @Test
    public void createCategoryTest(){
        Category category =dao.insertObject(new Category("test category"));
        assert category!=null: "problem with category";
    }

    @Test
    public void selectCategoryTest(){
        Category category =dao.insertObject(new Category("test category"));
        assert dao.selectObject(category.getId()).getName().equals("test category") : "problem with category";
    }

    @Test
    public void deleteCategoryTest(){
        Category category = dao.insertObject(new Category("тест категори"));
        assert dao.delete(category.getId()):"problem with category";
    }

    @Test
    public void updateCategoryTest(){
        Category category = dao.insertObject(new Category("тест категори"));
        dao.update(new Category(category.getId(),"test category update"));
        Category category1 = dao.selectObject(category.getId());
        assert category1.getName().equals("test category update") : "problem with category";
    }
}
