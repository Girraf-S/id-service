package by.hembar.idservice.config;

import by.hembar.idservice.entity.SecurityOptions;
import by.hembar.idservice.helper.Properties;
import by.hembar.idservice.repository.SecurityOptionsRepo;
import by.hembar.idservice.repository.UserRepository;
import by.hembar.idservice.service.UserDetailsServiceImpl;
import by.hembar.idservice.session.SessionStorage;
import by.hembar.idservice.session.SessionStorageMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Configuration
@EnableScheduling
public class BeansConfig {

    @Value("${security.encoder.strength}")
    private int strength;
    private final UserRepository userRepository;

    private final SecurityOptionsRepo securityOptionsRepo;

    public BeansConfig(UserRepository userRepository, SecurityOptionsRepo securityOptionsRepo) {
        this.userRepository = userRepository;
        this.securityOptionsRepo = securityOptionsRepo;
    }

    @Bean
    @DependsOn("propertiesBean")
    public SecurityOptions defaultSecurityOptions() {
        List<SecurityOptions> securityOptionsList = securityOptionsRepo.findAllByIsActual(true);

        if (securityOptionsList == null || securityOptionsList.isEmpty()) {
            return SecurityOptions.builder()
                    .jwtLifeTime(Properties.get().JWT_LIFE_TIME)
                    .sessionLifeTime(Properties.get().SESSION_LIFE_TIME)
                    .userTimeInBlock(Properties.get().USER_TIME_IN_BLOCK)
                    .adminKey(Properties.get().ADMIN_KEY)
                    .secretKey(Properties.get().SECRET_KEY)
                    .isActual(true)
                    .build();
        }
        if (securityOptionsList.size() == 1) {
            return securityOptionsList.get(0);
        }
        return null;
    }

//    @Bean
//    public SessionStorage sessionStorage() {
//        return SessionStorageMap.getInstance();
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
