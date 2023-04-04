package project.vapeshop.contoller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.VaporizerDTOFilter;
import project.vapeshop.dto.product.VaporizerDTOFullInfo;
import project.vapeshop.service.product.VaporizerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/vaporizers")
public class ControllerVaporizer {
    VaporizerService service;

    @Autowired
    public ControllerVaporizer(VaporizerService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid VaporizerDTOFullInfo vaporizerDTOFullInfo) {
        return new ResponseEntity<>(service.addItem(vaporizerDTOFullInfo), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<?> read(@RequestBody FilterRequest filterRequest) {
        return new ResponseEntity<>(service.showItems(filterRequest), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{vape-id}")
    public ResponseEntity<?> readId(@PathVariable("vape-id") Integer id) {
        return new ResponseEntity<>(service.showItem(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{vape-id}")
    public boolean delete(@PathVariable("vape-id") Integer id) {
        return service.deleteItem(id);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid VaporizerDTOFullInfo vaporizerDTOFullInfo) {
        return new ResponseEntity<>(service.updateItem(vaporizerDTOFullInfo), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/type/{typeVaporizer}")
    public ResponseEntity<?> showVaporizerTypeNicotine(@PathVariable("typeVaporizer") String type) {
        return new ResponseEntity<>(service.showVaporizerByType(type), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/filter")
    public ResponseEntity<?> readByFilter(@RequestBody VaporizerDTOFilter vaporizerDTOFilter){
        return new ResponseEntity<>(service.vaporizerByFilter(vaporizerDTOFilter),HttpStatus.OK);
    }
}
