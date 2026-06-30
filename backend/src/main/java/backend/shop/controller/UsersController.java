package backend.shop.controller;

import backend.shop.dto.UserDTO;
import backend.shop.dto.UserRegistrationDTO;
import backend.shop.dto.UserUpdateDTO;
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
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRegistrationDTO userDTO) {
        UserDTO registeredUser = this.usersService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
    //Do zmiany na lepsze
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> data){
        String email = data.get("email");
        if(this.usersService.restartPassword(email)){
            return new ResponseEntity<>("Haslo zostalo zmienione, wisomosc na mailu", HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Cos poszlo nie tak", HttpStatusCode.valueOf(404));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        this.usersService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Uzytkowik o ID: " + id + " zostal usuniety pomyslnie");
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @Valid @RequestBody UserUpdateDTO user){
        UserDTO updatedUser = this.usersService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);

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
        return ResponseEntity.status(HttpStatus.OK).body(activeAdmins);

    }

}