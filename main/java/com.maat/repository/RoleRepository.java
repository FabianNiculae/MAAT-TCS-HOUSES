package com.maat.repository;

import com.maat.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository adaptation for the Role entity.
 * @author Brand Hauser
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String name);

    boolean existsByRole(String role);

    Role findRoleByRole(String role);
}
