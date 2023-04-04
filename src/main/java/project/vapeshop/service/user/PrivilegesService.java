package project.vapeshop.service.user;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IPrivilegeDao;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.user.PrivilegesDTO;
import project.vapeshop.entity.user.Privileges;
import project.vapeshop.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
public class PrivilegesService {
    IPrivilegeDao dao;
    ModelMapper modelMapper;

    @Autowired
    public PrivilegesService(IPrivilegeDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public PrivilegesDTO showObject(int id) {
        try {
            return modelMapper.map(dao.selectObject(id), PrivilegesDTO.class);
        } catch (NoResultException noResultException) {
            throw new NotFoundException("privilege dont found");
        }
    }

    public List<PrivilegesDTO> showObjects(FilterRequest filterRequest) {
        List<Privileges> privileges=dao.selectObjects(filterRequest.getPageable());
        if(privileges.isEmpty()){
            throw new NotFoundException("privilege list is empty");
        }
        return privileges.stream()
                .map(p -> modelMapper.map(p, PrivilegesDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public PrivilegesDTO addObject(PrivilegesDTO privilegesDTO) {
        return modelMapper.map(dao.insertObject(modelMapper.map(privilegesDTO, Privileges.class)), PrivilegesDTO.class);
    }


    @Transactional
    public boolean deleteObject(int id) {
        try {
            return dao.delete(id);
        } catch (NoResultException noResultException) {
            throw new NotFoundException("privilege dont found");
        }
    }

    @Transactional
    public PrivilegesDTO updateObject(PrivilegesDTO privilegesDTO) {
        return modelMapper.map(dao.update(modelMapper.map(privilegesDTO, Privileges.class)), PrivilegesDTO.class);
    }
}
