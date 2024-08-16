package ru.ylib.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylib.dto.UserDTO;
import ru.ylib.models.User;
import ru.ylib.models.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role.name", target = "role")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "role", target = "role")
    User userDTOToUser(UserDTO userDTO);
}
