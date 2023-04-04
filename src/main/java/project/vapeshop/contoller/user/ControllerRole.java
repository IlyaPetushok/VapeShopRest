package project.vapeshop.contoller.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.user.PrivilegesDTO;
import project.vapeshop.dto.user.RoleDTO;
import project.vapeshop.service.user.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/roles")
public class ControllerRole {
    RoleService service;

    @Autowired
    public ControllerRole(RoleService service) {
        this.service = service;
    }


    @PreAuthorize("hasAuthority('CREATE_MODERATOR')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid RoleDTO roleDTO) {
        return new ResponseEntity<>(service.addObject(roleDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ_MODERATOR')")
    @GetMapping
    public ResponseEntity<?> read(@RequestBody FilterRequest filterRequest) {
        return new ResponseEntity<>(service.showObjects(filterRequest), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('READ_MODERATOR')")
    @GetMapping("/{role-id}")
    public ResponseEntity<?> read(@PathVariable("role-id") Integer id) {
        return new ResponseEntity<>(service.showObject(id), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('DELETE_MODERATOR')")
    @DeleteMapping("/{role-id}")
    public boolean delete(@PathVariable("role-id") Integer id) {
        return service.deleteObject(id);
    }

    @PreAuthorize("hasAuthority('UPDATE_MODERATOR')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid RoleDTO roleDTO) {
        return new ResponseEntity<>(service.updateObject(roleDTO), HttpStatus.UPGRADE_REQUIRED);
    }

    @GetMapping("/privilege")
    public ResponseEntity<?> readByPrivilege(@RequestBody PrivilegesDTO privilegesDTO) {
        return new ResponseEntity<>(service.showObjectFindPrivilege(privilegesDTO), HttpStatus.UPGRADE_REQUIRED);
    }
}
