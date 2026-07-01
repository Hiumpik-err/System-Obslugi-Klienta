package backend.shop.controller;

import backend.shop.dto.WarningDTO;
import backend.shop.model.Warnings;
import backend.shop.service.WarningsService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/warnings")
@CrossOrigin
@RestController
public class WarningsController {
    private final WarningsService warningsService;

    public WarningsController(WarningsService warningsService) {
        this.warningsService = warningsService;
    }

    @PostMapping("/new-warning")
    public ResponseEntity<String> addNewWarning(@RequestBody WarningDTO warning){
        this.warningsService.addNewWarning(warning);
        return ResponseEntity.status(HttpStatus.OK).body("Zgloszenie zostalo przyjete i zostanie rozpatrzone");
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllWarnings(){
        List<WarningDTO> warnings = this.warningsService.getAllWarnings();
        return ResponseEntity.status(HttpStatus.OK).body(warnings);
    }
}
