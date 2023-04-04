package project.vapeshop.service.product;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IVaporizerDao;
import project.vapeshop.dto.filter.CustomSortDirection;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.VaporizerDTOFilter;
import project.vapeshop.dto.product.VaporizerDTO;
import project.vapeshop.dto.product.VaporizerDTOFullInfo;
import project.vapeshop.entity.product.Vaporizer;
import project.vapeshop.entity.product.Vaporizer_;
import project.vapeshop.entity.type.VaporizerType;
import project.vapeshop.exception.NotFoundException;
import project.vapeshop.predicate.ComparisonType;
import project.vapeshop.predicate.CustomPredicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class VaporizerService {
    IVaporizerDao dao;
    ModelMapper modelMapper;

    @Autowired
    public VaporizerService(IVaporizerDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }


    public VaporizerDTOFullInfo showItem(int id) {
        try {
            return modelMapper.map(dao.selectObject(id), VaporizerDTOFullInfo.class);
        } catch (NoResultException e) {
            throw new NotFoundException( "vaporizer dont found");
        }
    }

    public List<VaporizerDTO> showItems(FilterRequest filterRequest) {
        List<Vaporizer> vaporizers = dao.selectObjects(filterRequest.getPageable());
        if (vaporizers.isEmpty()) {
            throw new NotFoundException("vaporizer list is empty");
        }
        return vaporizers.stream()
                .map(vaporizer -> modelMapper.map(vaporizer, VaporizerDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public VaporizerDTOFullInfo addItem(VaporizerDTOFullInfo vaporizerDTOFullInfo) {
        return modelMapper.map(dao.insertObject(modelMapper.map(vaporizerDTOFullInfo, Vaporizer.class)), VaporizerDTOFullInfo.class);
    }


    @Transactional
    public boolean deleteItem(int id) {
        try {
            return dao.delete(id);
        } catch (NoResultException e) {
            throw new NotFoundException("vaporizer dont found");
        }
    }

    @Transactional
    public VaporizerDTOFullInfo updateItem(VaporizerDTOFullInfo vaporizerDTOFullInfo) {
        return modelMapper.map(dao.update(modelMapper.map(vaporizerDTOFullInfo, Vaporizer.class)), VaporizerDTOFullInfo.class);
    }

    public List<VaporizerDTO> showVaporizerByType(String type) {
        List<Vaporizer> vaporizersList = dao.findByTypeVaporizer(VaporizerType.getTypeVaporizer(type));
        if (vaporizersList.isEmpty()) {
            throw new NotFoundException("vaporizer list is empty");
        }
        return vaporizersList.stream()
                .map(vape -> modelMapper.map(vape, VaporizerDTO.class))
                .collect(Collectors.toList());
    }

    public List<VaporizerDTO> vaporizerByFilter(VaporizerDTOFilter vaporizerDTOFilter) {
        Sort sort = Sort.by(CustomSortDirection.getSortDirection(vaporizerDTOFilter.getSortDirection()), vaporizerDTOFilter.getSortByName());
        Pageable pageable = PageRequest.of(vaporizerDTOFilter.getPage(), vaporizerDTOFilter.getSize(), sort);
        return dao.selectObjectsByFilter(generateCustomPredicate(vaporizerDTOFilter), pageable).stream()
                .map(vaporizer -> modelMapper.map(vaporizer, VaporizerDTO.class))
                .collect(Collectors.toList());
    }

    private List<CustomPredicate<?>> generateCustomPredicate(VaporizerDTOFilter vaporizerDTOFilter) {
        List<CustomPredicate<?>> predicates = new ArrayList<>();
        if (vaporizerDTOFilter.getResistance() != null && vaporizerDTOFilter.getResistanceMax() != null) {
            predicates.add(new CustomPredicate<>(vaporizerDTOFilter.getResistance(), vaporizerDTOFilter.getResistanceMax(), Vaporizer_.resistance, ComparisonType.BETWEEN, Double.class));
        } else {
            if (vaporizerDTOFilter.getResistance() != null) {
                predicates.add(new CustomPredicate<>(vaporizerDTOFilter.getResistance(), Vaporizer_.resistance, ComparisonType.MORE));
            }
            if (vaporizerDTOFilter.getResistanceMax() != null) {
                predicates.add(new CustomPredicate<>(vaporizerDTOFilter.getResistanceMax(), Vaporizer_.resistance, ComparisonType.LESS));
            }
        }
        if(vaporizerDTOFilter.getType()!=null){
            predicates.add(new CustomPredicate<>(vaporizerDTOFilter.getType(),Vaporizer_.type,ComparisonType.IN,String.class));
        }
        return predicates;
    }
}
