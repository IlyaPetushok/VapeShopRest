package project.vapeshop.service.common;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IRatingDao;
import project.vapeshop.dto.common.RatingDTOForProduct;
import project.vapeshop.dto.common.RatingDTOFullInfo;
import project.vapeshop.dto.filter.CustomSortDirection;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.RatingDTOFilter;
import project.vapeshop.entity.common.Rating;
import project.vapeshop.entity.common.Rating_;
import project.vapeshop.exception.AccessDeniedException;
import project.vapeshop.exception.NotFoundException;
import project.vapeshop.predicate.ComparisonType;
import project.vapeshop.predicate.CustomPredicate;
import project.vapeshop.security.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RatingService {
    IRatingDao dao;
    ModelMapper modelMapper;

    @Autowired
    public RatingService(IRatingDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }


    public RatingDTOForProduct showObject(int id) {
        try {
            return modelMapper.map(dao.selectObject(id), RatingDTOForProduct.class);
        } catch (NoResultException e) {
            throw new NotFoundException("operation is fail because rating dont found");
        }
    }

    public List<RatingDTOForProduct> showObjects(FilterRequest filterRequest) {
        List<Rating> ratings = dao.selectObjects(filterRequest.getPageable());
        if (ratings.isEmpty()) {
            throw new NotFoundException("ratings list is empty");
        }
        return ratings.stream()
                .map(rating -> modelMapper.map(rating, RatingDTOForProduct.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public RatingDTOFullInfo addObject(RatingDTOFullInfo ratingDTOFullInfo) {
        ratingDTOFullInfo = modelMapper.map(dao.insertObject(modelMapper.map(ratingDTOFullInfo, Rating.class)), RatingDTOFullInfo.class);
        return ratingDTOFullInfo;
    }


    @Transactional
    public boolean deleteObject(int id) {
        try {
            return dao.delete(id);
        } catch (NoResultException e) {
            throw new NotFoundException("rating dont found");
        }
    }

    @Transactional
    public RatingDTOForProduct updateObject(RatingDTOFullInfo ratingDTOFullInfo, CustomUserDetails customUserDetails) {
        Rating rating=dao.update(modelMapper.map(ratingDTOFullInfo, Rating.class));
        if(!rating.getUser().getId().equals(customUserDetails.getId())){
            throw new AccessDeniedException("rating access denied");
        }
        return modelMapper.map(rating, RatingDTOForProduct.class);
    }

    public List<RatingDTOForProduct> ratingByFilter(RatingDTOFilter ratingDTOFilter){
        Sort sort=Sort.by(CustomSortDirection.getSortDirection(ratingDTOFilter.getSortDirection()),ratingDTOFilter.getSortByName());
        Pageable pageable= PageRequest.of(ratingDTOFilter.getPage(),ratingDTOFilter.getSize(),sort);
        return dao.selectObjectsByFilter(generateCustomPredicates(ratingDTOFilter),pageable).stream()
                .map(rating -> modelMapper.map(rating,RatingDTOForProduct.class))
                .collect(Collectors.toList());
    }

    private List<CustomPredicate<?>> generateCustomPredicates(RatingDTOFilter ratingDTOFilter){
        List<CustomPredicate<?>> predicates=new ArrayList<>();
        if(ratingDTOFilter.getComment()!=null){
            predicates.add(new CustomPredicate<>(ratingDTOFilter.getComment(), Rating_.comment, ComparisonType.LIKE));
        }
        if(ratingDTOFilter.getQuantityStar()!=null && ratingDTOFilter.getQuantityStarMax()!=null){
            predicates.add(new CustomPredicate<>(ratingDTOFilter.getQuantityStar(),ratingDTOFilter.getQuantityStarMax(),Rating_.quantityStar,ComparisonType.BETWEEN,Integer.class));
        }else {
            if(ratingDTOFilter.getQuantityStar()!=null){
                predicates.add(new CustomPredicate<>(ratingDTOFilter.getQuantityStar(),Rating_.quantityStar,ComparisonType.MORE));
            }
            if(ratingDTOFilter.getQuantityStarMax()!=null){
                predicates.add(new CustomPredicate<>(ratingDTOFilter.getQuantityStarMax(),Rating_.quantityStar,ComparisonType.LESS));
            }
        }
        return predicates;
    }
}
