package by.hembar.idservice.repository;

import com.solbeg.nuserservice.entity.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {
    Optional<ActivationCode> findByCode(String code);

    @Modifying
    @Transactional
    @Query("delete from ActivationCode ac where ac.expiredAt<:localDateTime")
    void deleteActivationCodeByExpiredAtBefore(@Param("localDateTime") LocalDateTime localDateTime);
}
