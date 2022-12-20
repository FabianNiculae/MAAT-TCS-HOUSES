package com.maat.repository;

import com.maat.model.House;
import com.maat.model.HouseChangeRequest;
import com.maat.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository adaptation for the HouseChangeRequest entity.
 * @author Brand Hauser
 */
@Repository
public interface HouseChangeRequestRepository extends JpaRepository<HouseChangeRequest, Integer>, JpaSpecificationExecutor<HouseChangeRequest> {

    @Override
    @EntityGraph(value = "graph.ChangeRequest.simple", type = EntityGraph.EntityGraphType.FETCH)
    List<HouseChangeRequest> findAll();

    @EntityGraph(value = "graph.ChangeRequest.simple", type = EntityGraph.EntityGraphType.FETCH)
    List<HouseChangeRequest> findAllByStudent(Student studentId);

    boolean existsByStudentAndOldHouseAndTargetHouseAndExplanation(Student student, House oldHouse, House targetHouse, String explanation);

    @EntityGraph(value = "graph.ChangeRequest.simple", type = EntityGraph.EntityGraphType.FETCH)
    HouseChangeRequest getByStudentAndOldHouseAndTargetHouseAndExplanation(Student student, House oldHouse, House targetHouse, String explanation);

    void deleteByStudentAndOldHouseAndTargetHouseAndExplanation(Student student, House oldHouse, House targetHouse, String explanation);

    @Query(value = "select key, array_agg(distinct value)\n" +
            "from house_change_requests, jsonb_each_text(to_jsonb(house_change_requests))\n" +
            "where key != 'student' \n" +
            "and key != 'explanation'\n" +
            "and key != 'denial_explanation'\n" +
            "and key != 'id'\n" +
            "and key != 'request_date'\n" +
            "and key != 'decided_date'\n" +
            "group by key;", nativeQuery = true)
    List<Object> getFilterables();
}
