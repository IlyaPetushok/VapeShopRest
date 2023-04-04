package project.vapeshop.service.product;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IItemDao;
import project.vapeshop.dto.filter.CustomSortDirection;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.ItemDTOFilter;
import project.vapeshop.dto.product.ItemDTOFullInfo;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.entity.product.Item;
import project.vapeshop.entity.product.Item_;
import project.vapeshop.exception.NotFoundException;
import project.vapeshop.predicate.ComparisonType;
import project.vapeshop.predicate.CustomPredicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ItemService {
    private IItemDao dao;
    private ModelMapper modelMapper;

    public ItemService() {
    }

    @Autowired
    public ItemService(IItemDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }


    public ItemDTOFullInfo showItem(int id) {
        try {
            return modelMapper.map(dao.selectObject(id), ItemDTOFullInfo.class);
        } catch (NoResultException e) {
            throw new NotFoundException("item dont found");
        }
    }

    public List<ItemDTOInfoForCatalog> showItems(FilterRequest filterRequest) {
        List<Item> itemList = dao.selectObjects(filterRequest.getPageable());
        if (itemList.isEmpty()) {
            throw new NotFoundException("item list empty");
        }
        return itemList.stream()
                .map(item -> modelMapper.map(item, ItemDTOInfoForCatalog.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemDTOFullInfo addItem(ItemDTOFullInfo itemDTO) {
        return modelMapper.map(dao.insertObject(modelMapper.map(itemDTO, Item.class)), ItemDTOFullInfo.class);
    }


    @Transactional
    public boolean deleteItem(int id) {
        try {
            return dao.delete(id);
        } catch (NoResultException e) {
            throw new NotFoundException("item dont found");
        }
    }

    @Transactional
    public ItemDTOFullInfo updateItem(ItemDTOFullInfo itemDTOFullInfo) {
        return modelMapper.map(dao.update(modelMapper.map(itemDTOFullInfo, Item.class)), ItemDTOFullInfo.class);
    }

    public List<ItemDTOInfoForCatalog> showItemByCategory(String nameCategory) {
        List<Item> items = dao.selectFindByCategory(nameCategory);
        if (items.isEmpty()) {
            throw new NotFoundException("item list empty");
        }
        return items.stream()
                .map(item -> modelMapper.map(item, ItemDTOInfoForCatalog.class))
                .collect(Collectors.toList());
    }

    public List<ItemDTOInfoForCatalog> itemByFilter(ItemDTOFilter itemDTOFilter) {
        Sort sort = Sort.by(CustomSortDirection.getSortDirection(itemDTOFilter.getSortDirection()), itemDTOFilter.getSortByName());
        Pageable pageable = PageRequest.of(itemDTOFilter.getPage(), itemDTOFilter.getSize(), sort);
        return dao.selectObjectsByFilter(generateCustomPredicate(itemDTOFilter),pageable).stream()
                .map(item -> modelMapper.map(item,ItemDTOInfoForCatalog.class))
                .collect(Collectors.toList());
    }

    private List<CustomPredicate<?>> generateCustomPredicate(ItemDTOFilter itemDTOFilter) {
        List<CustomPredicate<?>> predicates=new ArrayList<>();
        if(itemDTOFilter.getPrice()!=null && itemDTOFilter.getPriceMax()!=null){
            predicates.add(new CustomPredicate<>(itemDTOFilter.getPrice(),itemDTOFilter.getPriceMax(),Item_.price, ComparisonType.BETWEEN, BigDecimal.class));
        }else {
            if(itemDTOFilter.getPrice()!=null){
                predicates.add(new CustomPredicate<>(itemDTOFilter.getPrice(),Item_.price,ComparisonType.EQUAL));
            }
            if(itemDTOFilter.getPriceMax()!=null){
                predicates.add(new CustomPredicate<>(itemDTOFilter.getPriceMax(),Item_.price,ComparisonType.EQUAL));
            }
        }
        if(itemDTOFilter.getName()!=null){
            predicates.add(new CustomPredicate<>(itemDTOFilter.getName(),Item_.name,ComparisonType.LIKE));
        }
        if(itemDTOFilter.getQuantity()!=null){
            predicates.add(new CustomPredicate<>(itemDTOFilter.getQuantity(),Item_.quantity,ComparisonType.MORE));
        }
        return predicates;
    }
}
