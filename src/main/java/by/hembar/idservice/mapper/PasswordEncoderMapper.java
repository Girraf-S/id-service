package by.hembar.idservice.mapper;

//import com.solbeg.nuserservice.entity.Role;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PasswordEncoderMapper {
    private final PasswordEncoder passwordEncoder;

    @Named(value = "encode")
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

//    @Named(value = "getAuthorities")
//    public Set<String> getAuthorities(Long userId) {
//
//        //return role.getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toSet());
//    }
}
