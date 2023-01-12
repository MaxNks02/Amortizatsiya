package com.example.amortizatsiya.controller;
import com.example.amortizatsiya.dto.UserDto;
import com.example.amortizatsiya.exception.handler.ResponseHandler;
import com.example.amortizatsiya.security.entity.Users;
import com.example.amortizatsiya.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController extends BaseController<UserService> {
    public UserController(UserService service) {
        super(service);
    }

    @GetMapping("/getByName")
    public ResponseEntity<?> getByUsername(@RequestParam String username) {
        Users user = service.findByUsername(username);
        return ResponseHandler.generateResponse(HttpStatus.OK, user);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return service.getAllUser();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        UserDto dto = service.saveUser(userDto);
        return ResponseHandler.generateResponse("successfully saved", HttpStatus.OK, dto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto userDto){
        UserDto dto = service.updateUser(userDto);
        return ResponseHandler.generateResponse("successfully updated", HttpStatus.OK, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseHandler.generateResponse("successfully deleted", HttpStatus.OK);
    }

}
