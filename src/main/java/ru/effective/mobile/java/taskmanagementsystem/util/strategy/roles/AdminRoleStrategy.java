package ru.effective.mobile.java.taskmanagementsystem.util.strategy.roles;

import org.springframework.stereotype.Service;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.RoleRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Role;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.RoleEnum;

@Service
public class AdminRoleStrategy implements RoleStrategy {

    private final RoleRepository roleRepository;

    public AdminRoleStrategy(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRole() {
        return roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }
}