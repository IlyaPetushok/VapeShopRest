package vapeshop.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import project.vapeshop.dao.ILiquideDao;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.product.LiquideDTO;
import project.vapeshop.dto.product.LiquideDTOFullInfo;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.product.Liquide;
import project.vapeshop.service.product.LiquideService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LiquideUnitTest {
    @InjectMocks
    private LiquideService liquideService;

    @Mock
    private ILiquideDao liquideDao;

    @Spy
    private ModelMapper modelMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private final Liquide liquide =new Liquide(new Item(1),"testLiquide", 45, "солевой", 30);;
    private final LiquideDTOFullInfo liquideDTOFullInfo =new LiquideDTOFullInfo(new ItemDTOInfoForCatalog(1),"testLiquideDto", 45, "солевой", 30);

    @Test
    public void testGetByIdCategory(){
        when(liquideDao.selectObject(1)).thenReturn(liquide);

        Assertions.assertEquals(liquideService.showItem(1).getFlavour(), liquide.getFlavour());
        verify(liquideDao,times(1)).selectObject(any());
    }

    @Test
    public void testGetAllCategory(){
        List<Liquide> list=new ArrayList<>();

        list.add(new Liquide(new Item(1),"testLiquide1", 45, "солевой", 30));
        list.add(new Liquide(new Item(1),"testLiquide2", 45, "солевой", 30));
        list.add(new Liquide(new Item(1),"testLiquide3", 45, "солевой", 30));

        when(liquideDao.selectObjects(any())).thenReturn(list);

        List<LiquideDTO> liquideDTOFullInfos = liquideService.showItems(any());
        for (int i = 0; i < liquideDTOFullInfos.size(); i++) {
            Assertions.assertEquals(liquideDTOFullInfos.get(i).getFlavour(),list.get(i).getFlavour());
        }
        verify(liquideDao,times(1)).selectObjects(any());
    }


    @Test
    public void testAddCategory(){
        when(liquideDao.insertObject(any())).thenReturn(liquide);

        Assertions.assertEquals(liquideService.addItem(liquideDTOFullInfo).getFlavour(), liquide.getFlavour());
        verify(liquideDao,times(1)).insertObject(any());
    }

    @Test
    public void testUpdateCategory(){
        when(liquideDao.update(any(Liquide.class))).thenReturn(liquide);

        Assertions.assertEquals(liquideService.updateItem(liquideDTOFullInfo).getFlavour(), liquide.getFlavour());
        verify(liquideDao,times(1)).update(any());
    }

    @Test
    public void testDeleteCategory(){
        when(liquideDao.delete(1)).thenReturn(true);
        Assertions.assertTrue(liquideService.deleteItem(1));
        verify(liquideDao,times(1)).delete(1);
    }
}
