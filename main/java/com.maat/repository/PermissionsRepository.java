package com.maat.repository;

import com.maat.model.Permissions;
import com.maat.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository adaptation for the Permissions entity.
 * @author Brand Hauser
 */
@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Integer> {
    boolean existsByRole(Role role);

    Permissions findPermissionsByRole(Role role);

    void deletePermissionsByRole(Role role);
}
