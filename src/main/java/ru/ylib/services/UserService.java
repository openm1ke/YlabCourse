package ru.ylib.services;

import ru.ylib.models.User;

public interface UserService {
    User create(User user);
    User read(long id);
    User update(User user);
    void delete(long id);
}
