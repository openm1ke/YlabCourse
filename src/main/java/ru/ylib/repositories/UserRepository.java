package ru.ylib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ylib.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Дополнительные методы для поиска пользователей по логину
    User findByLogin(String login);
}
