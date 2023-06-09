package ru.netology.mycloudstorage.repositopy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.mycloudstorage.modele.User;

@Repository
public interface MyUserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}