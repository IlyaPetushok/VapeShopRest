package vapeshop.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import project.vapeshop.dao.IUserDao;
import project.vapeshop.dao.impl.UserDao;
import project.vapeshop.dto.user.RoleDTO;
import project.vapeshop.dto.user.UserDTOAfterAuthorization;
import project.vapeshop.dto.user.UserDTOForRegistration;
import project.vapeshop.entity.user.Role;
import project.vapeshop.entity.user.User;
import project.vapeshop.exception.NotFoundException;
import project.vapeshop.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserUnitTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserDao userDao;

    @Spy
    private ModelMapper modelMapper;

    private final User user=new User(1,"Cluch","Vasya","Pupkin","login2","pass2","vasya@mail",new Role(1));
    private final UserDTOForRegistration userDto =new UserDTOForRegistration(2,"Cluch","Vasya","Pupkin","login2","pass2","vasya@mail",new RoleDTO(1));

    @Test
    public void testGetByIdCategory() throws NotFoundException {
        when(userDao.selectObject(1)).thenReturn(user);
        Assertions.assertEquals(userService.showObject(1).getId(), user.getId());
        verify(userDao,times(1)).selectObject(any());
    }

    @Test
    public void testGetAllCategory(){
        List<User> list=new ArrayList<>();

        list.add(new User(1,"Cluch","Vasya","Pupkin","login2","pass2","vasya@mail",new Role(1)));
        list.add(new User(2,"Cluch","Vasya","Pupkin","login2","pass2","vasya@mail",new Role(1)));
        list.add(new User(3,"Cluch","Vasya","Pupkin","login2","pass2","vasya@mail",new Role(1)));

        when(userDao.selectObjects(any())).thenReturn(list);

        List<UserDTOAfterAuthorization> vapeDTOS= userService.showObjects(any());
        for (int i = 0; i < vapeDTOS.size(); i++) {
            Assertions.assertEquals(vapeDTOS.get(i).getId(),list.get(i).getId());
        }
        verify(userDao,times(1)).selectObjects(any());
    }


    @Test
    public void testAddCategory(){
        when(userDao.insertObject(any())).thenReturn(user);

        Assertions.assertEquals(userService.addObject(userDto).getId(), user.getId());
        verify(userDao,times(1)).insertObject(any());
    }

    @Test
    public void testUpdateCategory(){
        when(userDao.update(any(User.class))).thenReturn(user);

        Assertions.assertEquals(userService.updateObject(userDto,any()).getId(), user.getId());
        verify(userDao,times(1)).update(any());
    }

    @Test
    public void testDeleteCategory(){
        when(userDao.delete(1)).thenReturn(true);
        Assertions.assertTrue(userService.deleteObject(1,any()));
        verify(userDao,times(1)).delete(1);
    }
}
