package com.maat.repository;

import com.maat.model.Student;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository adaptation for the Student entity.
 * @author Brand Hauser
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

    @Override
    @EntityGraph(value = "graph.Student.house.id", type = EntityGraph.EntityGraphType.FETCH)
    List<Student> findAll();

    @EntityGraph(value = "graph.Student.house.id", type = EntityGraph.EntityGraphType.FETCH)
    Student findByIdNumber(int id);

    @EntityGraph(value = "graph.Students.all", type = EntityGraph.EntityGraphType.FETCH)
    Student getByIdNumber(int id);

    void deleteByIdNumber(int id);

    @Query(value = "select key, array_agg(distinct value)\n" +
            "from students, jsonb_each_text(to_jsonb(students))\n" +
            "where key != 'id_number' \n" +
            "and key != 'first_name'\n" +
            "and key != 'last_name'\n" +
            "and key != 'email'\n" +
            "and key != 'date_assigned_to_house'\n" +
            "and key != 'link_name'\n" +
            "group by key;", nativeQuery = true)
    List<Object> getFilterables();

    @Modifying
    @Query(value = "INSERT INTO user_roles (user_id, role_name) VALUES (:#{#student.idNumber}, 'ROLE_STUDENT')", nativeQuery = true)
    void addStudentRole(@Param("student") Student student);

    @Modifying
    @Query(value = "delete from user_roles where user_id = :#{#idNumber}", nativeQuery = true)
    void deleteStudentRoles(@Param("idNumber") int idNumber);

}
