package backend.shop.service;

import backend.shop.model.Warnings;
import backend.shop.repo.WarningRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarningsService {
    private final WarningRepo repo;

    public WarningsService(WarningRepo repo) {
        this.repo = repo;
    }

    public boolean addNewWarning(Warnings warning) {
        try{
            this.repo.save(warning);
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Optional<List<Warnings>> getAllWarnings() {
        try{
            var res = this.repo.findByIssueStatusNot("zakonczony");
            return Optional.of(res);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
