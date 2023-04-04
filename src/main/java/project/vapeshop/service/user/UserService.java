package project.vapeshop.service.user;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IUserDao;
import project.vapeshop.dto.filter.CustomSortDirection;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.user.UserDTOAfterAuthorization;
import project.vapeshop.dto.filter.UserDTOFilter;
import project.vapeshop.dto.user.UserDTOForAuthorization;
import project.vapeshop.dto.user.UserDTOForRegistration;
import project.vapeshop.entity.user.User;
import project.vapeshop.entity.user.User_;
import project.vapeshop.exception.AccessDeniedException;
import project.vapeshop.exception.NotFoundException;
import project.vapeshop.exception.UnAuthorizationException;
import project.vapeshop.predicate.CustomPredicate;
import project.vapeshop.predicate.ComparisonType;
import project.vapeshop.security.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
public class UserService {
    IUserDao dao;
    ModelMapper modelMapper;

    @Autowired
    public UserService(IUserDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public UserDTOForRegistration showObject(int id) {
        User user;
        try {
            user = dao.selectObject(id);
        } catch (NoResultException exception) {
            throw new NotFoundException("user dont found");
        }
        return modelMapper.map(user, UserDTOForRegistration.class);
    }

    public List<UserDTOAfterAuthorization> showObjects(FilterRequest filterRequest) {
        List<User> users = dao.selectObjects(filterRequest.getPageable());
        if (users.isEmpty()) {
            throw new NotFoundException("list users is empty");
        }
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTOAfterAuthorization.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTOForRegistration addObject(UserDTOForRegistration userDTOForRegistration) {
        return modelMapper.map(dao.insertObject(modelMapper.map(userDTOForRegistration, User.class)), UserDTOForRegistration.class);
    }



    @Transactional
    public boolean deleteObject(int id,CustomUserDetails customUserDetails) {
        try {
            if(!dao.selectObject(id).getId().equals(customUserDetails.getId())){
                throw new AccessDeniedException("access denied user delete");
            }
            return dao.delete(id);
        } catch (NoResultException exception) {
            throw new NotFoundException("user dont found");
        }
    }

    @Transactional
    public UserDTOAfterAuthorization updateObject(UserDTOForRegistration userDTOForRegistration, CustomUserDetails customUserDetails) {
        User user=dao.update(modelMapper.map(userDTOForRegistration, User.class));
        if(!user.getId().equals(customUserDetails.getId())){
            throw new AccessDeniedException("access dinied update user");
        }
        return modelMapper.map(user, UserDTOAfterAuthorization.class);
    }


    public UserDTOAfterAuthorization userFindByLoginWithPassword(UserDTOForAuthorization userDTOForAuthorization) {
        try {
            return modelMapper.map(dao.findByLoginAndPassword(modelMapper.map(userDTOForAuthorization, User.class)), UserDTOAfterAuthorization.class);
        } catch (NoResultException exception) {
            throw new UnAuthorizationException("Check login or password");
        }
    }

    public User userFindByLogin(String login) {
        try {
            return dao.findByLogin(login);
        } catch (NoResultException resultException) {
            throw new NotFoundException("authorization please");
        }
    }

    public List<UserDTOAfterAuthorization> userFindByFilter(UserDTOFilter userDTOFilter) {
        Sort.Direction sortDirection=CustomSortDirection.getSortDirection(userDTOFilter.getSortDirection());
        Pageable pageable = PageRequest.of(userDTOFilter.getPage(), userDTOFilter.getSize(), Sort.by(sortDirection,userDTOFilter.getSortByName()));
        return dao.selectObjectsByFilter(generateCustomPredicate(userDTOFilter),pageable).stream()
                .map(user -> modelMapper.map(user, UserDTOAfterAuthorization.class))
                .collect(Collectors.toList());
    }

    private List<CustomPredicate<?>> generateCustomPredicate(UserDTOFilter userDTOFilter){
        List<CustomPredicate<?>> predicates = new ArrayList<>();
        if(userDTOFilter.getId()!=null){
            predicates.add(new CustomPredicate<>(userDTOFilter.getId(),User_.id,ComparisonType.LESS));
        }
        if (userDTOFilter.getName() != null) {
            predicates.add(new CustomPredicate<>(userDTOFilter.getName(), User_.name, ComparisonType.LIKE));
        }
        if (userDTOFilter.getPatronymic() != null) {
            predicates.add(new CustomPredicate<>(userDTOFilter.getPatronymic(), User_.patronymic, ComparisonType.LIKE));
        }
        if (userDTOFilter.getSurname() != null) {
            predicates.add(new CustomPredicate<>(userDTOFilter.getSurname(), User_.surname, ComparisonType.LIKE));
        }
        if (userDTOFilter.getLogin() != null) {
            predicates.add(new CustomPredicate<>(userDTOFilter.getLogin(), User_.login, ComparisonType.EQUAL));
        }
        if (userDTOFilter.getPassword() != null) {
            predicates.add(new CustomPredicate<>(userDTOFilter.getPassword(), User_.password, ComparisonType.EQUAL));
        }
        if (userDTOFilter.getMail() != null) {
            predicates.add(new CustomPredicate<>(userDTOFilter.getMail(), User_.mail, ComparisonType.EQUAL));
        }
        return predicates;
    }
}
