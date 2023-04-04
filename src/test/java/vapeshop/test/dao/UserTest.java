package vapeshop.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IUserDao;
import project.vapeshop.dao.impl.AbstractDao;
import project.vapeshop.entity.user.Role;
import project.vapeshop.entity.user.User;
import vapeshop.test.config.H2Config;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class})
@Transactional
@WebAppConfiguration
public class UserTest {

    @Autowired
    private IUserDao dao;

    @Test
    public void createUserTest(){
        User user=dao.insertObject(new User("Petushok","Ilya","Aleksandrovich","log","pas","a331@mail",new Role(1)));
        assert dao.selectObject(user.getId()).getName().equals("Ilya") : "проблемы с user create";
    }

    @Test
    public void selectUserTest(){
        assert dao.selectObject(1).getName().equals("Илья") : "проблемы с user select";
    }

    @Test
    public void deleteUserTest(){
        User user=dao.insertObject(new User("Petushok","Ilya","Aleksandrovich","logi","pas1s","a323@mail",new Role(1)));
        assert dao.delete(user.getId()):"проблемы с user delete";
    }

    @Test
    public void updateUserTest(){
        User user=dao.update(new User(1,"Petushok","Ilya Update","Aleksandrovich","login12","pass","a1233@mail",new Role(1)));
        assert dao.selectObject(user.getId()).getName().equals("Ilya Update") : "проблемы с user update";
    }
}
