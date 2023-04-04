package project.vapeshop.contoller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.product.CategoryDTO;
import project.vapeshop.service.product.CategoryService;

import javax.validation.Valid;


@RestController
@RequestMapping("/categories")
public class ControllerCategory {
    CategoryService service;

    @Autowired
    public ControllerCategory(CategoryService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('CREATE_CATEGORY')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid CategoryDTO categoryDTO) throws Exception {
        return new ResponseEntity<>(service.addObject(categoryDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{category-id}")
    public ResponseEntity<?> readId(@PathVariable("category-id") Integer id){
        return new ResponseEntity<>(service.showObject(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<?> read(@RequestBody FilterRequest filterRequest) {
        return new ResponseEntity<>(service.showObjects(filterRequest), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    @DeleteMapping("/{category-id}")
    public boolean delete(@PathVariable("category-id") Integer id) {
        return service.deleteObject(id);
    }


    @PreAuthorize("hasAuthority('UPDATE_CATEGORY')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(service.updateObject(categoryDTO), HttpStatus.UPGRADE_REQUIRED);
    }
}
