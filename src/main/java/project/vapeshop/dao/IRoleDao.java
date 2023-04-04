package project.vapeshop.dao;

import project.vapeshop.entity.user.Role;

import java.util.List;

public interface IRoleDao extends Dao<Role,Integer>{
    List<Role> selectFindByPrivilege(String namePrivilege);
}
