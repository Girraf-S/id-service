package by.hembar.idservice.component;

import com.solbeg.nuserservice.repository.ActivationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DeleteExpiredCode {

    private final ActivationCodeRepository activationCodeRepository;

    @Scheduled(cron = "0 0 4 * * ?")
    public void clearExpiredCode(){
        activationCodeRepository.deleteActivationCodeByExpiredAtBefore(LocalDateTime.now());
    }
}
