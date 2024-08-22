package ru.ylib.services;

import ru.ylib.dto.UserDTO;

import java.util.List;

/**
 * This class implements the CRUDService interface for User objects.
 */
public interface UserService {
    UserDTO create(UserDTO userDTO);
    UserDTO read(long id);
    UserDTO update(UserDTO userDTO);
    void delete(long id);
    List<UserDTO> readAll();
}
