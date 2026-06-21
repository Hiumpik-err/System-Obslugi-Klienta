package backend.shop.controller;

import backend.shop.model.Warnings;
import backend.shop.service.WarningsService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/warnings")
@CrossOrigin
@RestController
public class WarningsController {
    private final WarningsService warningsService;

    public WarningsController(WarningsService warningsService) {
        this.warningsService = warningsService;
    }

    @PostMapping("/new-warning")
    public ResponseEntity<?> addNewWarning(@RequestBody Warnings warning){
        if(this.warningsService.addNewWarning(warning)){
            return new ResponseEntity<>("Operacja udala sie", HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Operacja nie udała się", HttpStatusCode.valueOf(404));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllWarnings(){
        var warnings = this.warningsService.getAllWarnings();
        if(warnings.isPresent()){
            return new ResponseEntity<>(warnings.get(), HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Nie znaleziono", HttpStatusCode.valueOf(404));
    }
}
