package project.vapeshop.contoller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.LiquideDTOFilter;
import project.vapeshop.dto.product.LiquideDTOFullInfo;
import project.vapeshop.service.product.LiquideService;

import javax.validation.Valid;

@RestController
@RequestMapping("/liquides")
public class ControllerLiquide {
    LiquideService service;

    @Autowired
    public ControllerLiquide(LiquideService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid LiquideDTOFullInfo liquideDTOFullInfo) {
        return new ResponseEntity<>(service.addItem(liquideDTOFullInfo), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{liquide-id}")
    public ResponseEntity<?> readId(@PathVariable("liquide-id") Integer id){
        return new ResponseEntity<>(service.showItem(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<?> read(@RequestBody FilterRequest filterRequest) {
        return new ResponseEntity<>(service.showItems(filterRequest), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{liquide-id}")
    public boolean delete(@PathVariable("liquide-id") Integer id) {
        return service.deleteItem(id);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid LiquideDTOFullInfo liquideDTOFullInfo) {
        return new ResponseEntity<>(service.updateItem(liquideDTOFullInfo), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/type/{typeNicotine}")
    public ResponseEntity<?> showLiquideTypeNicotine(@PathVariable("typeNicotine") String typeNicotine){
        return new ResponseEntity<>(service.showLiquideByNicotine(typeNicotine), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/filter")
    public ResponseEntity<?> readByFilter(@RequestBody LiquideDTOFilter liquideDTOFilter){
        return new ResponseEntity<>(service.liquideByFilter(liquideDTOFilter),HttpStatus.OK);
    }
}
