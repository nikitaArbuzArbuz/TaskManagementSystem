package ru.effective.mobile.java.taskmanagementsystem.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByLogin(String username);

    Boolean existsByEmail(String email);
}
