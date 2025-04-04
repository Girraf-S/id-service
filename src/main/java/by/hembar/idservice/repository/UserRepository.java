package by.hembar.idservice.repository;

import by.hembar.idservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);

    @Modifying
    @Query(value = "update users set is_active=true where id=:id", nativeQuery = true)
    void activateUserById(Long id);
}
