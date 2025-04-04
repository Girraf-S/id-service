package by.hembar.idservice.repository;

import by.hembar.idservice.entity.UserArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserArchiveRepository extends JpaRepository<UserArchive, Long> {
}
