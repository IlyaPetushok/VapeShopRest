package vapeshop.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import project.vapeshop.dao.IRoleDao;
import project.vapeshop.dao.impl.RoleDao;
import project.vapeshop.dto.user.RoleDTO;
import project.vapeshop.entity.user.Role;
import project.vapeshop.service.user.RoleService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RoleUnitTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private RoleService roleService;

    @Mock
    private IRoleDao roleDao;

    @Spy
    private ModelMapper modelMapper;

    private final Role role =new Role(1,"zamAdmin");
    private final RoleDTO roleDTO =new RoleDTO(2,"zamAdmin");

    @Test
    public void testGetByIdCategory(){
        when(roleDao.selectObject(1)).thenReturn(role);

        Assertions.assertEquals(roleService.showObject(1).getId(), role.getId());
        verify(roleDao,times(1)).selectObject(any());
    }

    @Test
    public void testGetAllCategory(){
        List<Role> list=new ArrayList<>();

        list.add(new Role(1,"zamAdmin"));
        list.add(new Role(2,"zamAdmin"));
        list.add(new Role(1,"zamAdmin"));

        when(roleDao.selectObjects(any())).thenReturn(list);

        List<RoleDTO> roleDTOS= roleService.showObjects(any());
        for (int i = 0; i < roleDTOS.size(); i++) {
            Assertions.assertEquals(roleDTOS.get(i).getId(),list.get(i).getId());
        }
        verify(roleDao,times(1)).selectObjects(any());
    }


    @Test
    public void testAddCategory(){
        when(roleDao.insertObject(any())).thenReturn(role);

        Assertions.assertEquals(roleService.addObject(roleDTO).getId(), role.getId());
        verify(roleDao,times(1)).insertObject(any());
    }

    @Test
    public void testUpdateCategory(){
        when(roleDao.update(any(Role.class))).thenReturn(role);

        Assertions.assertEquals(roleService.updateObject(roleDTO).getId(), role.getId());
        verify(roleDao,times(1)).update(any());
    }

    @Test
    public void testDeleteCategory(){
        when(roleDao.delete(1)).thenReturn(true);
        Assertions.assertTrue(roleService.deleteObject(1));
        verify(roleDao,times(1)).delete(1);
    }
}
