package kz.edy.astanait.product.controller.admin;

import kz.edy.astanait.product.dto.requestDto.admin.CreateUsersDtoRequest;
import kz.edy.astanait.product.dto.responseDto.security.RoleDtoResponse;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.service.admin.CreateUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/admin")
public class AdminController extends ExceptionHandling {

    private final CreateUsersService createUsersService;

    @Autowired
    public AdminController(CreateUsersService createUsersService) {
        this.createUsersService = createUsersService;
    }

    @GetMapping("/get-roles")
    public ResponseEntity<List<RoleDtoResponse>> getRoles() {
        return new ResponseEntity<>(createUsersService.getRoles(), HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody CreateUsersDtoRequest createUsersDtoRequest) {
        createUsersService.createUser(createUsersDtoRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
