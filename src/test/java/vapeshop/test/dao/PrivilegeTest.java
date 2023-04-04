package vapeshop.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.Dao;
import project.vapeshop.dao.IPrivilegeDao;
import project.vapeshop.entity.user.Privileges;
import vapeshop.test.config.H2Config;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {H2Config.class})
@Transactional
@WebAppConfiguration
public class PrivilegeTest {

    @Autowired
    private IPrivilegeDao dao;

    @Test
    public void createPrivilegeTest(){
        Privileges privileges=dao.insertObject(new Privileges("test priv"));
        assert dao.selectObject(privileges.getId()).getName().equals("test priv") : "проблемы с privilege create";
    }

    @Test
    public void selectPrivilegeTest(){
        Privileges privileges=dao.insertObject(new Privileges("test priv"));
        assert dao.selectObject(privileges.getId()).getName().equals("test priv") : "проблемы с privilege select";
    }

    @Test
    public void deletePrivilegeTest(){
        Privileges privileges=dao.insertObject(new Privileges("delete user"));
        assert dao.delete(privileges.getId()):"проблемы с privilege delete";
    }

    @Test
    public void updatePrivilegeTest(){
        Privileges privileges=dao.insertObject(new Privileges("priv"));
        dao.update(new Privileges(privileges.getId(), "priv update"));
        assert dao.selectObject(privileges.getId()).getName().equals("priv update") : "проблемы с privilege update";
    }
}
