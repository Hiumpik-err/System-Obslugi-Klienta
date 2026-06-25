package backend.shop.controller;

import backend.shop.dto.UserDTO;
import backend.shop.exceptions.UserAlreadyExistsException;
import backend.shop.model.Users;
import backend.shop.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UsersController{

    private final UsersService usersService;
    public UsersController(UsersService us){
        this.usersService = us;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        Users registeredUser = this.usersService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> data){
        String email = data.get("email");
        if(this.usersService.restartPassword(email)){
            return new ResponseEntity<>("Haslo zostalo zmienione, wisomosc na mailu", HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Cos poszlo nie tak", HttpStatusCode.valueOf(404));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        if(this.usersService.deleteUser(id)){
            return new ResponseEntity<>("User deleted Successfuly", HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>("User deleted unsuccessfuly", HttpStatusCode.valueOf(400));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody Users user){
        var updatedUser = this.usersService.updateUser(id, user);
        if(updatedUser.isPresent()){
            return new ResponseEntity<>(updatedUser.get(), HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Something went wrong", HttpStatusCode.valueOf(400));
    }

    @PatchMapping("/{id}/set-active")
    public ResponseEntity<?> setActive(@PathVariable int id){
        boolean done = this.usersService.setActive(id);
        if(done){
            return new ResponseEntity<>("Done", HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Done", HttpStatusCode.valueOf(404));

    }

    @GetMapping("/active-admins")
    public ResponseEntity<?> getActiveAdmins() {
        var activeAdmins = this.usersService.getActiveAdmins();
        if (activeAdmins.isPresent()) {
            return new ResponseEntity<>(activeAdmins.get(), HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Error", HttpStatusCode.valueOf(400));
    }

}