package backend.shop.repo;

import backend.shop.model.Warnings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WarningRepo extends JpaRepository<Warnings, Integer> {
    List<Warnings> findByIssueStatusNot(String status);
}
