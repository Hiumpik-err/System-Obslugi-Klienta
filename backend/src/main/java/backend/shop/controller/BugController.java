package backend.shop.controller;

import backend.shop.model.Bug;
import backend.shop.service.BugService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/bugs")
@RestController
@CrossOrigin
public class BugController {
    private final BugService bugService;
    public BugController(BugService bugService) {
        this.bugService = bugService;
    }
    @PostMapping("/add-new")
    public ResponseEntity<?> addNewBug(@RequestBody Bug bug){
        if(this.bugService.addNewBug(bug)){
            return new ResponseEntity<>("Operacja udana", HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Operacja nie udana", HttpStatusCode.valueOf(400));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBugs(){
        Optional<List<Bug>> bugs = this.bugService.getAllBugs();
        if(bugs.isPresent()){
            return new ResponseEntity<>(bugs.get(), HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Lista pusta lub cos poszlo nie tak", HttpStatusCode.valueOf(404));
    }



}
