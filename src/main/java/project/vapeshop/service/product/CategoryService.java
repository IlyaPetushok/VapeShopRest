package project.vapeshop.service.product;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vapeshop.dao.ICategoryDao;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.product.CategoryDTO;
import project.vapeshop.entity.product.Category;
import project.vapeshop.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class CategoryService {
    ICategoryDao dao;
    ModelMapper modelMapper;


    public CategoryService() {
    }

    @Autowired
    public CategoryService(ICategoryDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }


    public CategoryDTO showObject(int id) {
        try {
            return modelMapper.map(dao.selectObject(id), CategoryDTO.class);
        } catch (NoResultException e) {
            throw new NotFoundException("category dont found");
        }
    }

    public List<CategoryDTO> showObjects(FilterRequest filterRequest) {
        List<Category> categories = dao.selectObjects(filterRequest.getPageable());
        if (categories.isEmpty()) {
            throw new NotFoundException("category dont found");
        }
        return categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public CategoryDTO addObject(CategoryDTO categoryDTO) {
        return modelMapper.map(dao.insertObject(modelMapper.map(categoryDTO, Category.class)), CategoryDTO.class);
    }


    @Transactional
    public boolean deleteObject(int id) {
        try {
            return dao.delete(id);
        } catch (NoResultException e) {
            throw new NotFoundException("category dont found");
        }
    }

    @Transactional
    public CategoryDTO updateObject(CategoryDTO categoryDTO) {
        return modelMapper.map(dao.update(modelMapper.map(categoryDTO, Category.class)), CategoryDTO.class);
    }
}
