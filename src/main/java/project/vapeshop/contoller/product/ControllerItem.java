package project.vapeshop.contoller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.ItemDTOFilter;
import project.vapeshop.dto.filter.RatingDTOFilter;
import project.vapeshop.dto.product.ItemDTOFullInfo;
import project.vapeshop.dto.product.ItemDTOInfoForCatalog;
import project.vapeshop.service.product.CategoryService;
import project.vapeshop.service.product.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ControllerItem {
    ItemService itemService;

    @Autowired
    public ControllerItem(ItemService itemService) {
        this.itemService = itemService;
    }


    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid ItemDTOFullInfo item) {
        return new ResponseEntity<>(itemService.addItem(item), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<List<ItemDTOInfoForCatalog>> read(@RequestBody FilterRequest filterRequest) {
        return new ResponseEntity<>(itemService.showItems(filterRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{item-id}")
    public ResponseEntity<?> readId(@PathVariable("item-id") Integer id) {
        return new ResponseEntity<>(itemService.showItem(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{item-id}")
    public boolean delete(@PathVariable("item-id") Integer id) {
        return itemService.deleteItem(id);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid ItemDTOFullInfo itemDTOFullInfo) {
        return new ResponseEntity<>(itemService.updateItem(itemDTOFullInfo), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/category/{category}")
    public ResponseEntity<?> readItemByCategory(@PathVariable("category") String nameCategory) {
        return new ResponseEntity<>(itemService.showItemByCategory(nameCategory), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/filter")
    public ResponseEntity<?> readByFilter(@RequestBody ItemDTOFilter itemDTOFilter){
        return new ResponseEntity<>(itemService.itemByFilter(itemDTOFilter),HttpStatus.OK);
    }
}
