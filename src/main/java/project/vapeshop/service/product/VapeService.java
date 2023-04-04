package project.vapeshop.service.product;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.IVapeDao;
import project.vapeshop.dto.filter.CustomSortDirection;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.VapeDTOFilter;
import project.vapeshop.dto.product.VapeDTO;
import project.vapeshop.dto.product.VapeDTOFullInfo;
import project.vapeshop.entity.product.Vape;
import project.vapeshop.entity.product.Vape_;
import project.vapeshop.entity.type.VapeType;
import project.vapeshop.exception.NotFoundException;
import project.vapeshop.predicate.ComparisonType;
import project.vapeshop.predicate.CustomPredicate;

//import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class VapeService {
    IVapeDao dao;
    ModelMapper modelMapper;

    @Autowired
    public VapeService(IVapeDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public VapeDTOFullInfo showItem(int id) {
        try {
            return modelMapper.map(dao.selectObject(id), VapeDTOFullInfo.class);
        } catch (NoResultException e) {
            throw new NotFoundException("vape dont found");
        }
    }

    public List<VapeDTO> showItems(FilterRequest filterRequest) {
        List<Vape> vapes = dao.selectObjects(filterRequest.getPageable());
        if (vapes.isEmpty()) {
            throw new NotFoundException("vape list is empty");
        }
        return vapes.stream()
                .map(vape -> modelMapper.map(vape, VapeDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public VapeDTOFullInfo addItem(VapeDTOFullInfo vapeDTOFullInfo) {
        return modelMapper.map(dao.insertObject(modelMapper.map(vapeDTOFullInfo, Vape.class)), VapeDTOFullInfo.class);
    }



    @Transactional
    public boolean deleteItem(int id) {
        try {
            return dao.delete(id);
        } catch (NoResultException e) {
            throw new NotFoundException("vape dont found");
        }
    }

    @Transactional
    public VapeDTOFullInfo updateItem(VapeDTOFullInfo vapeDTOFullInfo) {
        return modelMapper.map(dao.update(modelMapper.map(vapeDTOFullInfo, Vape.class)), VapeDTOFullInfo.class);
    }

    public List<VapeDTO> showVapeByType(String type) {
        List<Vape> vapes=dao.findByTypeVape(VapeType.getTypeValue(type));
        if(vapes.isEmpty()){
            throw new NotFoundException("vape list is empty");
        }
        return vapes.stream()
                .map(vape -> modelMapper.map(vape, VapeDTO.class))
                .collect(Collectors.toList());
    }

    public List<VapeDTO> vapeByFilter(VapeDTOFilter vapeDTOFilter){
        Sort sort=Sort.by(CustomSortDirection.getSortDirection(vapeDTOFilter.getSortDirection()), vapeDTOFilter.getSortByName());
        Pageable pageable= PageRequest.of(vapeDTOFilter.getPage(), vapeDTOFilter.getSize(),sort);
        return dao.selectObjectsByFilter(generateCustomPredicate(vapeDTOFilter),pageable).stream()
                .map(vape -> modelMapper.map(vape,VapeDTO.class))
                .collect(Collectors.toList());
    }

    private List<CustomPredicate<?>> generateCustomPredicate(VapeDTOFilter vapeDTOFilter){
        List<CustomPredicate<?>> predicates=new ArrayList<>();
        if(vapeDTOFilter.getBattery()!=null && vapeDTOFilter.getBatteryMax()!=null){
            predicates.add(new CustomPredicate<>(vapeDTOFilter.getBattery(), vapeDTOFilter.getBatteryMax(), Vape_.battery, ComparisonType.BETWEEN,Integer.class));
        }else {
            if(vapeDTOFilter.getBatteryMax()!=null){
                predicates.add(new CustomPredicate<>(vapeDTOFilter.getBatteryMax(), Vape_.battery, ComparisonType.LESS));
            }
            if(vapeDTOFilter.getBattery()!=null){
                predicates.add(new CustomPredicate<>(vapeDTOFilter.getBattery(), Vape_.battery, ComparisonType.MORE));
            }
        }
        if(vapeDTOFilter.getType()!=null){
            predicates.add(new CustomPredicate<>(vapeDTOFilter.getType(),Vape_.type,ComparisonType.IN,String.class));
        }
        if(vapeDTOFilter.getPower()!=null && vapeDTOFilter.getPowerMax()!=null){
            predicates.add(new CustomPredicate<>(vapeDTOFilter.getPower(), vapeDTOFilter.getPowerMax(),Vape_.power,ComparisonType.BETWEEN,Integer.class));
        }else {
            if(vapeDTOFilter.getPowerMax()!=null){
                predicates.add(new CustomPredicate<>(vapeDTOFilter.getPowerMax(),Vape_.power,ComparisonType.LESS));
            }
            if(vapeDTOFilter.getPower()!=null){
                predicates.add(new CustomPredicate<>(vapeDTOFilter.getPower(),Vape_.power,ComparisonType.MORE));
            }
        }
        return predicates;
    }
}
