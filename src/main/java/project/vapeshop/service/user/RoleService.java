package project.vapeshop.service.user;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IRoleDao;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.user.PrivilegesDTO;
import project.vapeshop.dto.user.RoleDTO;
import project.vapeshop.entity.user.Role;
import project.vapeshop.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
public class RoleService {
    IRoleDao dao;
    ModelMapper modelMapper;

    @Autowired
    public RoleService(IRoleDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public RoleDTO showObject(int id) {
        try {
            return modelMapper.map(dao.selectObject(id), RoleDTO.class);
        } catch (NoResultException noResultException) {
            throw new NotFoundException("role doesnt found");
        }
    }

    public List<RoleDTO> showObjects(FilterRequest filterRequest) {
        List<Role> roles = dao.selectObjects(filterRequest.getPageable());
        if (roles.isEmpty()) {
            throw new NotFoundException("list roles is empty");
        }
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public RoleDTO addObject(RoleDTO roleDTO) {
        return modelMapper.map(dao.insertObject(modelMapper.map(roleDTO, Role.class)), RoleDTO.class);
    }


    @Transactional
    public boolean deleteObject(int id) {
        try {
            return dao.delete(id);
        }catch (NoResultException e){
            throw new NotFoundException("operation is fail because role dont found");
        }
    }

    @Transactional
    public RoleDTO updateObject(RoleDTO roleDTO) {
        return modelMapper.map(dao.update(modelMapper.map(roleDTO, Role.class)), RoleDTO.class);
    }

    public List<RoleDTO> showObjectFindPrivilege(PrivilegesDTO privileges) {
        try {
            return dao.selectFindByPrivilege(privileges.getName()).stream()
                    .map(role -> modelMapper.map(role, RoleDTO.class))
                    .collect(Collectors.toList());
        }catch (NoResultException noResultException){
            throw new NotFoundException("dont found role by privilege");
        }
    }
}
