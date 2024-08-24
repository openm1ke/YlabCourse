package ru.ylib.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ylib.dto.UserDTO;
import ru.ylib.mappers.UserMapper;
import ru.ylib.models.User;
import ru.ylib.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") long id) {
        System.out.println("Received GET request for user with ID: " + id);
        if(id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.read(id);
        if (user != null) {
            UserDTO userDTO = userMapper.userToUserDTO(user);
            System.out.println("User found: " + userDTO);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        User createdUser = userService.create(user);
        UserDTO createdUserDTO = userMapper.userToUserDTO(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") long id, @RequestBody @Valid UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.setId(id);
        User updatedUser = userService.update(user);
        if (updatedUser != null) {
            UserDTO updatedUserDTO = userMapper.userToUserDTO(updatedUser);
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
