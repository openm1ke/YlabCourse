package ru.ylib.mappers;

import org.mapstruct.Mapper;
import ru.ylib.dto.UserDTO;
import ru.ylib.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
