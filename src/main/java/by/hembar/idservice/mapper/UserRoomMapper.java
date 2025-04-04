package by.hembar.idservice.mapper;

import by.hembar.idservice.entity.UserRoom;
import by.hembar.idservice.model.CreateRoomRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoomMapper {
    @Mapping(target = "registrationDate", expression = "java(LocalDateTime.now())")
    UserRoom toUserRoom(CreateRoomRequest createRoomRequest);
}
