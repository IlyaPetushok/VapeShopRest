package project.vapeshop.contoller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.vapeshop.dto.common.OrderDTOFullInfo;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.OrderDTOFilter;
import project.vapeshop.dto.user.UserDTOForCommon;
import project.vapeshop.security.CustomUserDetails;
import project.vapeshop.service.common.OrderService;

@RestController
@RequestMapping("/orders")
public class ControllerOrder {
    OrderService service;


    @Autowired
    public ControllerOrder(OrderService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody OrderDTOFullInfo orderDTOFullInfo) {
            return new ResponseEntity<>(service.addObject(orderDTOFullInfo), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<?> read(@RequestBody FilterRequest filterRequest) {
            return new ResponseEntity<>(service.showObjects(filterRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{order-id}")
    public ResponseEntity<?> read(@PathVariable("order-id") Integer id,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
            return new ResponseEntity<>(service.showObject(id,customUserDetails), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/{order-id}")
    public boolean delete(@PathVariable("order-id") int id,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return service.deleteObject(id,customUserDetails);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/status/{status}")
    public ResponseEntity<?> readByStatus(@PathVariable("status") String status){
            return new ResponseEntity<>(service.showObjectsFindByStatus(status), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody OrderDTOFullInfo orderDTOFullInfo,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
            return new ResponseEntity<>(service.updateObject(orderDTOFullInfo,customUserDetails), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @PostMapping("/users")
    public ResponseEntity<?> readByUser(@RequestBody UserDTOForCommon userDTOForCommon) {
        return new ResponseEntity<>(service.showObjectsFindByUser(userDTOForCommon), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/filter")
    public ResponseEntity<?> readByFilter(@RequestBody OrderDTOFilter orderDTOFilter){
        return new ResponseEntity<>(service.orderFindByFilter(orderDTOFilter),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{order-id}/by")
    public ResponseEntity<?> ordersBy(@PathVariable("order-id") Integer id,@AuthenticationPrincipal CustomUserDetails customUserDetails){
        OrderDTOFullInfo orderDTOFullInfo=service.showObject(id,customUserDetails);
        return new ResponseEntity<>(service.orderSystemSale(orderDTOFullInfo),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/history")
    public ResponseEntity<?> ordersHistory(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return new ResponseEntity<>(service.orderByUserHistory(customUserDetails.getId()),HttpStatus.OK);
    }


}
