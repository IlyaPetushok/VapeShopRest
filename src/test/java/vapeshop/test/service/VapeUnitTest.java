package vapeshop.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import project.vapeshop.dao.IVapeDao;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.product.VapeDTO;
import project.vapeshop.dto.product.VapeDTOFullInfo;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.product.Vape;
import project.vapeshop.service.product.VapeService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VapeUnitTest {
    @InjectMocks
    private VapeService vapeService;

    @Mock
    private IVapeDao vapeDao;

    @Spy
    private ModelMapper modelMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private final Vape vape =new Vape(1,120,22450,"Мод",new Item(2));;
    private final VapeDTOFullInfo vapeDtoFullInfo =new VapeDTOFullInfo(2,120,22450,"Мод", new ItemDTOInfoForCatalog(2));

    @Test
    public void testGetByIdCategory(){
        when(vapeDao.selectObject(1)).thenReturn(vape);

        Assertions.assertEquals(vapeService.showItem(1).getId(), vape.getId());
        verify(vapeDao,times(1)).selectObject(any());
    }

    @Test
    public void testGetAllCategory(){
        List<Vape> list=new ArrayList<>();

        list.add(new Vape(1,120,22450,"Мод",new Item(2)));
        list.add(new Vape(2,120,22450,"Мод",new Item(2)));
        list.add(new Vape(3,120,22450,"Мод",new Item(2)));

        when(vapeDao.selectObjects(any())).thenReturn(list);

        List<VapeDTO> vapeDTOFullInfos = vapeService.showItems(any());
        for (int i = 0; i < vapeDTOFullInfos.size(); i++) {
            Assertions.assertEquals(vapeDTOFullInfos.get(i).getId(),list.get(i).getId());
        }
        verify(vapeDao,times(1)).selectObjects(any());
    }


    @Test
    public void testAddCategory(){
        when(vapeDao.insertObject(any())).thenReturn(vape);

        Assertions.assertEquals(vapeService.addItem(vapeDtoFullInfo).getId(), vape.getId());
        verify(vapeDao,times(1)).insertObject(any());
    }

    @Test
    public void testUpdateCategory(){
        when(vapeDao.update(any(Vape.class))).thenReturn(vape);

        Assertions.assertEquals(vapeService.updateItem(vapeDtoFullInfo).getId(), vape.getId());
        verify(vapeDao,times(1)).update(any());
    }

    @Test
    public void testDeleteCategory(){
        when(vapeDao.delete(1)).thenReturn(true);
        Assertions.assertTrue(vapeService.deleteItem(1));
        verify(vapeDao,times(1)).delete(1);
    }
}
