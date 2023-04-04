package vapeshop.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import project.vapeshop.dao.impl.RatingDao;
import project.vapeshop.dto.common.RatingDTOForProduct;
import project.vapeshop.dto.common.RatingDTOFullInfo;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.dto.user.UserDTOAfterAuthorization;
import project.vapeshop.entity.common.Rating;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.user.User;
import project.vapeshop.service.common.RatingService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RatingUnitTest {
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingDao ratingDao;

    @Spy
    private ModelMapper modelMapper;

    private final Rating rating = new Rating(1,"good", 5, new Item(1), new User(1));
    private final RatingDTOFullInfo ratingDTOFullInfo = new RatingDTOFullInfo(1,"good", 5, new ItemDTOInfoForCatalog(1), new UserDTOAfterAuthorization(1));

    @Test
    public void testGetByIdRating() {
        when(ratingDao.selectObject(1)).thenReturn(rating);
    }


    @Test
    public void testGetAllOrders(){
        List<Rating> list=new ArrayList<>();
        list.add(new Rating(1,"good", 5, new Item(1), new User(1)));
        list.add(new Rating(2,"good", 5, new Item(1), new User(1)));
        list.add(new Rating(3,"good", 5, new Item(1), new User(1)));

        when(ratingDao.selectObjects(any())).thenReturn(list);
        List<RatingDTOForProduct> ratings=ratingService.showObjects(any());
        for (int i = 0; i < ratings.size(); i++) {
            Assertions.assertEquals(ratings.get(i).getId(),list.get(i).getId());
        }
        verify(ratingDao,times(1)).selectObjects(any());
    }

    @Test
    public void testUpdateOrder(){
        when(ratingDao.update(any(Rating.class))).thenReturn(rating);
        Assertions.assertEquals(ratingService.updateObject(ratingDTOFullInfo,any()).getId(),rating.getId());
        verify(ratingDao,times(1)).update(any());
    }

    @Test
    public void testDeleteOrder(){
        when(ratingDao.delete(1)).thenReturn(true);
        Assertions.assertTrue(ratingService.deleteObject(1));
        verify(ratingDao,times(1)).delete(1);
    }
}
