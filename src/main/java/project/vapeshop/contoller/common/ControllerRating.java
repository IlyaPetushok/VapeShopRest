package project.vapeshop.contoller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.vapeshop.dto.common.RatingDTOFullInfo;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.RatingDTOFilter;
import project.vapeshop.security.CustomUserDetails;
import project.vapeshop.service.common.RatingService;

@RestController
@RequestMapping("/ratings")
public class ControllerRating {
    RatingService service;

    @Autowired
    public ControllerRating(RatingService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody RatingDTOFullInfo ratingDTOFullInfo) {
            return new ResponseEntity<>(service.addObject(ratingDTOFullInfo), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<?> read(@RequestBody FilterRequest filterRequest) {
            return new ResponseEntity<>(service.showObjects(filterRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{rating-id}")
    public ResponseEntity<?> readId(@PathVariable("rating-id") Integer id) {
            return new ResponseEntity<>(service.showObject(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE_RATING')")
    @DeleteMapping("/{rating-id}")
    public boolean delete(@PathVariable("rating-id") Integer id) {
        return service.deleteObject(id);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody RatingDTOFullInfo ratingDTOFullInfo, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return new ResponseEntity<>(service.updateObject(ratingDTOFullInfo,customUserDetails), HttpStatus.UPGRADE_REQUIRED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/filter")
    public ResponseEntity<?> readByFilter(@RequestBody RatingDTOFilter ratingDTOFilter){
        return new ResponseEntity<>(service.ratingByFilter(ratingDTOFilter),HttpStatus.OK);
    }
}

