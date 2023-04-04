package project.vapeshop.contoller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.UserDTOFilter;
import project.vapeshop.dto.user.UserDTOForRegistration;
import project.vapeshop.security.CustomUserDetails;
import project.vapeshop.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class ControllerUser {
    UserService service;

    @Autowired
    public ControllerUser(UserService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('CREATE_MODERATOR')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid UserDTOForRegistration userDTOForRegistration) {
        return new ResponseEntity<>(service.addObject(userDTOForRegistration), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<?> read(@RequestBody FilterRequest filterRequest) {
        return new ResponseEntity<>(service.showObjects(filterRequest), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/{user-id}")
    public boolean delete(@PathVariable("user-id") Integer id,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return service.deleteObject(id,customUserDetails);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{user-id}")
    public ResponseEntity<?> read(@PathVariable("user-id") Integer id)  {
        return new ResponseEntity<>(service.showObject(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid UserDTOForRegistration userDTOForRegistration, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return new ResponseEntity<>(service.updateObject(userDTOForRegistration,customUserDetails), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/filter")
    public ResponseEntity<?> readByFilter(@RequestBody UserDTOFilter userDTOFilter){
        return new ResponseEntity<>(service.userFindByFilter(userDTOFilter),HttpStatus.OK);
    }
}
