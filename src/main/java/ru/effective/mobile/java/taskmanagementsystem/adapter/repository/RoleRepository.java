package ru.effective.mobile.java.taskmanagementsystem.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Role;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.RoleEnum;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
