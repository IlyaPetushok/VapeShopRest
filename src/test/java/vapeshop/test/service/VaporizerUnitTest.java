package vapeshop.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import project.vapeshop.dao.IVaporizerDao;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.product.VaporizerDTO;
import project.vapeshop.dto.product.VaporizerDTOFullInfo;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.product.Vaporizer;
import project.vapeshop.service.product.VaporizerService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VaporizerUnitTest {
    @InjectMocks
    private VaporizerService vaporizerService;

    @Mock
    private IVaporizerDao vaporizerDao;

    @Spy
    private ModelMapper modelMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private final Vaporizer vaporizer =new Vaporizer(1,1.8,"картридж",new Item(6));;
    private final VaporizerDTOFullInfo vaporizerDtoFullInfo =new VaporizerDTOFullInfo(2,1.8,"картридж",new ItemDTOInfoForCatalog(6));

    @Test
    public void testGetByIdCategory(){
        when(vaporizerDao.selectObject(1)).thenReturn(vaporizer);

        Assertions.assertEquals(vaporizerService.showItem(1).getId(), vaporizer.getId());
        verify(vaporizerDao,times(1)).selectObject(any());
    }

    @Test
    public void testGetAllCategory(){
        List<Vaporizer> list=new ArrayList<>();

        list.add(new Vaporizer(2,1.8,"картридж",new Item(6)));
        list.add(new Vaporizer(2,1.8,"картридж",new Item(6)));
        list.add(new Vaporizer(2,1.8,"картридж",new Item(6)));

        when(vaporizerDao.selectObjects(any())).thenReturn(list);

        List<VaporizerDTO> vaporizerDTOFullInfos = vaporizerService.showItems(any());
        for (int i = 0; i < vaporizerDTOFullInfos.size(); i++) {
            Assertions.assertEquals(vaporizerDTOFullInfos.get(i).getId(),list.get(i).getId());
        }
        verify(vaporizerDao,times(1)).selectObjects(any());
    }


    @Test
    public void testAddCategory(){
        when(vaporizerDao.insertObject(any())).thenReturn(vaporizer);

        Assertions.assertEquals(vaporizerService.addItem(vaporizerDtoFullInfo).getId(), vaporizer.getId());
        verify(vaporizerDao,times(1)).insertObject(any());
    }

    @Test
    public void testUpdateCategory(){
        when(vaporizerDao.update(any(Vaporizer.class))).thenReturn(vaporizer);

        Assertions.assertEquals(vaporizerService.updateItem(vaporizerDtoFullInfo).getId(), vaporizer.getId());
        verify(vaporizerDao,times(1)).update(any());
    }

    @Test
    public void testDeleteCategory(){
        when(vaporizerDao.delete(1)).thenReturn(true);
        Assertions.assertTrue(vaporizerService.deleteItem(1));
        verify(vaporizerDao,times(1)).delete(1);
    }
}
