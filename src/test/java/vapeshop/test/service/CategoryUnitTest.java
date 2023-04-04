package vapeshop.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import project.vapeshop.dao.ICategoryDao;
import project.vapeshop.dto.product.CategoryDTO;
import project.vapeshop.entity.product.Category;
import project.vapeshop.service.product.CategoryService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CategoryUnitTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private ICategoryDao categoryDao;

    @Spy
    private ModelMapper modelMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private final Category category=new Category("Liquide");
    private final CategoryDTO categoryDTO=new CategoryDTO("LiquideDto");

    @Test
    public void testGetByIdCategory(){
        when(categoryDao.selectObject(1)).thenReturn(category);

        Assertions.assertEquals(categoryService.showObject(1).getName(),category.getName());
        verify(categoryDao,times(1)).selectObject(any());
    }

    @Test
    public void testGetAllCategory(){
        List<Category> list=new ArrayList<>();

        list.add(new Category("test1"));
        list.add(new Category("test2"));
        list.add(new Category("test3"));

        when(categoryDao.selectObjects(any())).thenReturn(list);

        List<CategoryDTO> categories=categoryService.showObjects(any());
        for (int i = 0; i < categories.size(); i++) {
            Assertions.assertEquals(categories.get(i).getName(),list.get(i).getName());
        }
        verify(categoryDao,times(1)).selectObjects(any());
    }


    @Test
    public void testAddCategory(){
        when(categoryDao.insertObject(any())).thenReturn(category);

        Assertions.assertEquals(categoryService.addObject(categoryDTO).getName(),category.getName());
        verify(categoryDao,times(1)).insertObject(any());
    }

    @Test
    public void testUpdateCategory(){
        when(categoryDao.update(any(Category.class))).thenReturn(category);

        Assertions.assertEquals(categoryService.updateObject(categoryDTO).getName(),category.getName());
        verify(categoryDao,times(1)).update(any());
    }

    @Test
    public void testDeleteCategory(){
        when(categoryDao.delete(1)).thenReturn(true);
        Assertions.assertTrue(categoryService.deleteObject(1));
        verify(categoryDao,times(1)).delete(1);
    }
}
