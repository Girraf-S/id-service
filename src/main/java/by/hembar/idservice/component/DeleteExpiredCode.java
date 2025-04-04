package by.hembar.idservice.component;

//import com.solbeg.nuserservice.repository.ActivationCodeRepository;

import by.hembar.idservice.repository.ActivationCodeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DeleteExpiredCode {

    private final ActivationCodeRepository activationCodeRepository;

    public DeleteExpiredCode(ActivationCodeRepository activationCodeRepository) {
        this.activationCodeRepository = activationCodeRepository;
    }

    @Scheduled(cron = "0 0 4 * * ?")
    public void clearExpiredCode() {
        activationCodeRepository.deleteActivationCodeByExpiredAtBefore(LocalDateTime.now());
    }
}
