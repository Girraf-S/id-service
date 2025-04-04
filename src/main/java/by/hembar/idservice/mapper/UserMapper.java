package by.hembar.idservice.mapper;

//import com.solbeg.nuserservice.entity.Role;
//import com.solbeg.nuserservice.entity.User;
//import com.solbeg.nuserservice.entity.UserArchive;
//import com.solbeg.nuserservice.model.RegisterRequest;
//import com.solbeg.nuserservice.model.UserArchiveResponse;
//import com.solbeg.nuserservice.model.UserResponse;
//import by.hembar.idservice.entity.Role;
import by.hembar.idservice.entity.User;
import by.hembar.idservice.entity.UserArchive;
import by.hembar.idservice.model.RegisterRequest;
import by.hembar.idservice.model.UserArchiveResponse;
import by.hembar.idservice.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PasswordEncoderMapper.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "registerRequest.password", qualifiedByName = "encode")
    User registerRequestToUser(RegisterRequest registerRequest, boolean isActive);

    @Mapping(target = "username", source = "user.email")
    @Mapping(target = "isActive", source = "user.active")
    @Mapping(target = "isEmailVerified", source = "user.emailVerified")
    //@Mapping(target = "authorities", source = "user.id", qualifiedByName = "getAuthorities")
    UserResponse userToUserResponse(User user);


    @Mapping(target = "isActive", source = "user.active")
    UserArchiveResponse userToUserArchiveResponse(User user);


    @Mapping(target = "isActive", source = "userArchive.active")
    UserArchiveResponse userArchiveToUserArchiveResponse(UserArchive userArchive);
}
