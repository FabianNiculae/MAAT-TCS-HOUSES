package com.maat.repository;

import com.maat.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository adaptation for the ChangeLog entity.
 */
@Repository
public interface ChangeLogRepository extends JpaRepository<Student, Integer> {
}
