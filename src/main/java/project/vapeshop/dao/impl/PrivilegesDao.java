package project.vapeshop.dao.impl;

import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import project.vapeshop.dao.IPrivilegeDao;
import project.vapeshop.entity.user.Privileges;
//import javax.persistence.Query;
import java.util.List;

@Repository
public class PrivilegesDao extends AbstractDao<Privileges,Integer> implements IPrivilegeDao {
//    @Override
//    public List<Privileges> selectObjects() {
//        Query query=entityManager.createQuery("Select priv from Privileges as priv");
//        return query.getResultList();
//    }

    @Override
    public Privileges selectObject(Integer id) {
        return entityManager.find(Privileges.class,id);
    }

}
