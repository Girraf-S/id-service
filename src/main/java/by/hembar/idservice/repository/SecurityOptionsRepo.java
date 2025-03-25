package by.hembar.idservice.repository;

import by.hembar.idservice.entity.SecurityOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityOptionsRepo extends JpaRepository<SecurityOptions, Long> {

    List<SecurityOptions> findAllByIsActual(Boolean isActual);
}
