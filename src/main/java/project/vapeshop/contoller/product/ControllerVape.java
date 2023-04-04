package project.vapeshop.contoller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.LiquideDTOFilter;
import project.vapeshop.dto.filter.VapeDTOFilter;
import project.vapeshop.dto.product.VapeDTOFullInfo;
import project.vapeshop.service.product.VapeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/vapes")
public class ControllerVape {
    VapeService service;

    @Autowired
    public ControllerVape(VapeService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid VapeDTOFullInfo vapeDTOFullInfo) {
        return new ResponseEntity<>(service.addItem(vapeDTOFullInfo), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{vape-id}")
    public ResponseEntity<?> readId(@PathVariable("vape-id") Integer id){
        return new ResponseEntity<>(service.showItem(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<?> read(@RequestBody FilterRequest filterRequest) {
        return new ResponseEntity<>(service.showItems(filterRequest), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{vape-id}")
    public boolean delete(@PathVariable("vape-id") Integer id) {
        return service.deleteItem(id);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid VapeDTOFullInfo vapeDTOFullInfo) {
        return new ResponseEntity<>(service.updateItem(vapeDTOFullInfo), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/type/{typeVape}")
    public ResponseEntity<?> showVapeTypeNicotine(@PathVariable("typeVape") String type){
        return new ResponseEntity<>(service.showVapeByType(type), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/filter")
    public ResponseEntity<?> readByFilter(@RequestBody VapeDTOFilter vapeDTOFilter){
        return new ResponseEntity<>(service.vapeByFilter(vapeDTOFilter),HttpStatus.OK);
    }
}
