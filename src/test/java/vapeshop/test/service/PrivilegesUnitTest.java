package vapeshop.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import project.vapeshop.dao.IPrivilegeDao;
import project.vapeshop.dao.impl.PrivilegesDao;
import project.vapeshop.dto.user.PrivilegesDTO;
import project.vapeshop.entity.user.Privileges;
import project.vapeshop.service.user.PrivilegesService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PrivilegesUnitTest {


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private PrivilegesService privilegesService;

    @Mock
    private IPrivilegeDao privilegesDao;

    @Spy
    private ModelMapper modelMapper;

    private final Privileges privileges =new Privileges(1,"DeleteUser");
    private final PrivilegesDTO privilegesDTO =new PrivilegesDTO(2,"DeleteUser");

    @Test
    public void testGetByIdCategory(){
        when(privilegesDao.selectObject(1)).thenReturn(privileges);

        Assertions.assertEquals(privilegesService.showObject(1).getId(), privileges.getId());
        verify(privilegesDao,times(1)).selectObject(any());
    }

    @Test
    public void testGetAllCategory(){
        List<Privileges> list=new ArrayList<>();

        list.add(new Privileges(1,"DeleteUser"));
        list.add(new Privileges(2,"DeleteUser"));
        list.add(new Privileges(3,"DeleteUser"));

        when(privilegesDao.selectObjects(any())).thenReturn(list);

        List<PrivilegesDTO> vapeDTOS= privilegesService.showObjects(any());
        for (int i = 0; i < vapeDTOS.size(); i++) {
            Assertions.assertEquals(vapeDTOS.get(i).getId(),list.get(i).getId());
        }
        verify(privilegesDao,times(1)).selectObjects(any());
    }


    @Test
    public void testAddCategory(){
        when(privilegesDao.insertObject(any())).thenReturn(privileges);

        Assertions.assertEquals(privilegesService.addObject(privilegesDTO).getId(), privileges.getId());
        verify(privilegesDao,times(1)).insertObject(any());
    }

    @Test
    public void testUpdateCategory(){
        when(privilegesDao.update(any(Privileges.class))).thenReturn(privileges);

        Assertions.assertEquals(privilegesService.updateObject(privilegesDTO).getId(), privileges.getId());
        verify(privilegesDao,times(1)).update(any());
    }

    @Test
    public void testDeleteCategory(){
        when(privilegesDao.delete(1)).thenReturn(true);
        Assertions.assertTrue(privilegesService.deleteObject(1));
        verify(privilegesDao,times(1)).delete(1);
    }
}
