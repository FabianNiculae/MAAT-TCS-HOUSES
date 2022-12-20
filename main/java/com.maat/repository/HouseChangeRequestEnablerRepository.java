package com.maat.repository;

import com.maat.model.HouseChangeRequestsEnabler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository adaptation for the HouseChangeRequestEnabler entity.
 */
@Repository
public interface HouseChangeRequestEnablerRepository extends JpaRepository<HouseChangeRequestsEnabler, Integer> {
}
