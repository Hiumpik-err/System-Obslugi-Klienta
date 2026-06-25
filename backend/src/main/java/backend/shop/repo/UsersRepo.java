package backend.shop.repo;

import backend.shop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {
    Optional<Users> getByEmail(String email);

    @Query("SELECT u FROM Users u JOIN FETCH u.role WHERE :role MEMBER OF u.role AND u.isActive = true")
    List<Users> findAllActiveByRoleWithRoles(@Param("role") String role);

    boolean existsByEmail(String email);
}