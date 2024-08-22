package ru.ylib.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylib.dto.UserDTO;
import ru.ylib.models.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
}
