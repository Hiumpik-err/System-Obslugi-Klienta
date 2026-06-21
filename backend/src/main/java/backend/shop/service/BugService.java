package backend.shop.service;

import backend.shop.model.Bug;
import backend.shop.repo.BugRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BugService {
    private final BugRepo repo;

    public BugService(BugRepo repo) {
        this.repo = repo;
    }

    public boolean addNewBug(Bug bug) {
        try{
            this.repo.save(bug);
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Optional<List<Bug>> getAllBugs() {
        try{
            List<Bug> bugs = this.repo.findAll();
            if(bugs.isEmpty()) throw new Exception("Pusta lista");
            return Optional.of(bugs);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return Optional.empty();
        }
    }
}
