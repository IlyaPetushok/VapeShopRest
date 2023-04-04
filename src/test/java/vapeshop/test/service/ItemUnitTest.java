package vapeshop.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import project.vapeshop.dao.IItemDao;
import project.vapeshop.dto.product.ItemDTOFullInfo;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.entity.product.Category;
import project.vapeshop.entity.product.Item;
import project.vapeshop.service.product.ItemService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ItemUnitTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private IItemDao dao;

    @Spy
    private ModelMapper modelMapper;

    private static final Item item = new Item("photoest", "test product", new Category(), new BigDecimal(Double.toString(23.0)), 10);
    private static final ItemDTOFullInfo itemDtoFullInfo = new ItemDTOFullInfo("path/photo4", "Baby Plus", new Category(), new BigDecimal(Double.toString(15.0)), 10);


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetByIdItem() {
        when(dao.selectObject(1)).thenReturn(item);
        ItemDTOFullInfo response = itemService.showItem(1);
        verify(dao, times(1)).selectObject(1);
        Assertions.assertEquals(response.getName(), item.getName());
    }

    @Test
    public void testGetAllItems() {
        List<Item> list = new ArrayList<>();

        list.add(new Item(1, "photoest", "test product1", new Category(), new BigDecimal(Double.toString(23.0)), 10));
        list.add(new Item(2, "photoest", "test product2", new Category(), new BigDecimal(Double.toString(23.0)), 10));
        list.add(new Item(3, "photoest", "test product3", new Category(), new BigDecimal(Double.toString(23.0)), 10));

        when(dao.selectObjects(any())).thenReturn(list);

        List<ItemDTOInfoForCatalog> list1 = itemService.showItems(any());
        for (int i = 0; i < list1.size(); i++) {
            Assertions.assertEquals(list1.get(i).getName(), list.get(i).getName());
        }
        verify(dao, times(1)).selectObjects(any());
    }

    @Test
    public void testAddItem() {
        when(dao.insertObject(any(Item.class))).thenReturn(item);

        Assertions.assertEquals(itemService.addItem(itemDtoFullInfo).getName(), item.getName());
    }

    @Test
    public void testGetUpdateItem() {

        when(dao.update(any(Item.class))).thenReturn(item);
        Assertions.assertEquals(itemService.updateItem(itemDtoFullInfo).getName(), "test product");
        verify(dao, times(1)).update(any());
    }
}
