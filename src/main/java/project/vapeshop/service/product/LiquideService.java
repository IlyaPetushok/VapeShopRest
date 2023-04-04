package project.vapeshop.service.product;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.ILiquideDao;
import project.vapeshop.dto.filter.CustomSortDirection;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.LiquideDTOFilter;
import project.vapeshop.dto.product.LiquideDTO;
import project.vapeshop.dto.product.LiquideDTOFullInfo;
import project.vapeshop.entity.product.Liquide;
import project.vapeshop.entity.product.Liquide_;
import project.vapeshop.entity.type.LiquideTypeNicotine;
import project.vapeshop.exception.NotFoundException;
import project.vapeshop.predicate.ComparisonType;
import project.vapeshop.predicate.CustomPredicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
public class LiquideService {
    ILiquideDao dao;
    ModelMapper modelMapper;

    @Autowired
    public LiquideService(ILiquideDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public LiquideDTOFullInfo showItem(int id) {
        try {
            return modelMapper.map(dao.selectObject(id), LiquideDTOFullInfo.class);
        } catch (NoResultException e) {
            throw new NotFoundException("liquide dont found");
        }
    }

    public List<LiquideDTO> showItems(FilterRequest filterRequest) {
        List<Liquide> liquideList = dao.selectObjects(filterRequest.getPageable());
        if (liquideList.isEmpty()) {
            throw new NotFoundException("liquide list is empty");
        }
        return liquideList.stream()
                .map(liquide -> modelMapper.map(liquide, LiquideDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public LiquideDTOFullInfo addItem(LiquideDTOFullInfo liquideDTOFullInfo) {
        return modelMapper.map(dao.insertObject(modelMapper.map(liquideDTOFullInfo, Liquide.class)), LiquideDTOFullInfo.class);
    }


    @Transactional
    public boolean deleteItem(int id) {
        try {
            return dao.delete(id);
        } catch (NoResultException e) {
            throw new NotFoundException("liquide dont found");
        }
    }

    @Transactional
    public LiquideDTOFullInfo updateItem(LiquideDTOFullInfo liquideDTOFullInfo) {
        return modelMapper.map(dao.update(modelMapper.map(liquideDTOFullInfo, Liquide.class)), LiquideDTOFullInfo.class);
    }

    public List<LiquideDTO> showLiquideByNicotine(String typeNicotine) {
        List<Liquide> liquideList=dao.findByTypeNicotine(LiquideTypeNicotine.getTypeValue(typeNicotine));
        if(liquideList.isEmpty()){
            throw new NotFoundException("liquide dont found");
        }
        return liquideList.stream()
                .map(liquide -> modelMapper.map(liquide, LiquideDTO.class))
                .collect(Collectors.toList());
    }

    public List<LiquideDTO> liquideByFilter(LiquideDTOFilter liquideDTOFilter){
        Sort sort=Sort.by(CustomSortDirection.getSortDirection(liquideDTOFilter.getSortDirection()), liquideDTOFilter.getSortByName());
        Pageable pageable= PageRequest.of(liquideDTOFilter.getPage(), liquideDTOFilter.getSize(),sort);
        return dao.selectObjectsByFilter(generateCustomPredicate(liquideDTOFilter),pageable).stream()
                .map(liquide -> modelMapper.map(liquide,LiquideDTO.class))
                .collect(Collectors.toList());
    }

    private List<CustomPredicate<?>> generateCustomPredicate(LiquideDTOFilter liquideDTOFilter){
        List<CustomPredicate<?>> predicates=new ArrayList<>();
        if(liquideDTOFilter.getTypeNicotine()!=null){
            predicates.add(new CustomPredicate<>(liquideDTOFilter.getTypeNicotine(), Liquide_.typeNicotine, ComparisonType.EQUAL));
        }
        if(liquideDTOFilter.getVolume()!=null && liquideDTOFilter.getVolumeMax()!=null){
            predicates.add(new CustomPredicate<>(liquideDTOFilter.getVolume(), liquideDTOFilter.getVolumeMax(), Liquide_.volume,ComparisonType.BETWEEN,Integer.class));
        }else {
            if(liquideDTOFilter.getVolume()!=null){
                predicates.add(new CustomPredicate<>(liquideDTOFilter.getVolume(), Liquide_.volume,ComparisonType.MORE));
            }
            if(liquideDTOFilter.getVolumeMax()!=null){
                predicates.add(new CustomPredicate<>(liquideDTOFilter.getVolumeMax(), Liquide_.volume,ComparisonType.LESS));
            }
        }
        if(liquideDTOFilter.getFlavour()!=null){
            predicates.add(new CustomPredicate<>(liquideDTOFilter.getFlavour(),Liquide_.flavour,ComparisonType.IN,String.class));
        }
        if(liquideDTOFilter.getFortress()!=null && liquideDTOFilter.getFortressMax()!=null){
            predicates.add(new CustomPredicate<>(liquideDTOFilter.getFortress(),liquideDTOFilter.getFortressMax(),Liquide_.fortress,ComparisonType.BETWEEN,Integer.class));
            if(liquideDTOFilter.getFortress()!=null){
                predicates.add(new CustomPredicate<>(liquideDTOFilter.getFortress(),Liquide_.fortress,ComparisonType.EQUAL));
            }
            if(liquideDTOFilter.getFortressMax()!=null){
                predicates.add(new CustomPredicate<>(liquideDTOFilter.getFortress(),Liquide_.fortress,ComparisonType.EQUAL));
            }
        }
        return predicates;
    }
}
