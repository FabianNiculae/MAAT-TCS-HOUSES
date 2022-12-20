package com.maat.repository;

import com.maat.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository adaptation for the Privilege entity.
 * @author Brand Hauser
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    Privilege findByPrivilege(String privilege);
}
