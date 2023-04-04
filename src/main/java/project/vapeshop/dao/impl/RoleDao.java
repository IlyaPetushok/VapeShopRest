package project.vapeshop.dao.impl;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import project.vapeshop.dao.IRoleDao;
import project.vapeshop.entity.user.Privileges;
import project.vapeshop.entity.user.Privileges_;
import project.vapeshop.entity.user.Role;
import project.vapeshop.entity.user.Role_;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Join;
//import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class RoleDao extends AbstractDao<Role,Integer> implements IRoleDao {

    public static final String SELECT_ROLE = "SELECT role from Role as role";

//    @Override
//    public List<Role> selectObjects() {
//        Query query= entityManager.createQuery(SELECT_ROLE);
//        return query.getResultList();
//    }

    @Override
    public Role selectObject(Integer id) {
        return entityManager.find(Role.class,id);
    }


    @Override
    public List<Role> selectFindByPrivilege(String namePrivilege) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery= criteriaBuilder.createQuery(Role.class);
        Root<Role> roleRoot=criteriaQuery.from(Role.class);
        Join<Role, Privileges> privilegesJoin=roleRoot.join(Role_.privileges);
        criteriaQuery.where(criteriaBuilder.equal(privilegesJoin.get(Privileges_.name),namePrivilege));
        TypedQuery<Role> query= entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
