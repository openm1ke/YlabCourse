package ru.ylib.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylib.dto.UserDTO;
import ru.ylib.models.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
