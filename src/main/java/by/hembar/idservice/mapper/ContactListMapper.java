package by.hembar.idservice.mapper;

import by.hembar.idservice.entity.ContactList;
import by.hembar.idservice.model.ContactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactListMapper {

    @Mapping(target = "userRoomId", source = "contactList.ownerUser.id")
    ContactResponse toContactResponse(ContactList contactList);

}
