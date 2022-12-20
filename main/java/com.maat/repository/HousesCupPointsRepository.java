package com.maat.repository;

import com.maat.model.HousesCupPoints;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * JPA Repository adaptation for the HouseCupPoints entity.
 * @author Brand Hauser
 */
@Repository
public interface HousesCupPointsRepository extends JpaRepository<HousesCupPoints, Integer>, JpaSpecificationExecutor {

    @Override
    @EntityGraph(value = "graph.Student.house.id", type = EntityGraph.EntityGraphType.FETCH)
    List<HousesCupPoints> findAll();

    HousesCupPoints findByDate(Date date);

    boolean existsByDate(Date date);

    @Query(value = "SELECT house, sum(points) FROM houses_cup\n" +
            "WHERE academic_year = :#{#year}\n" +
            "GROUP BY house", nativeQuery = true)
    List<Object> getPointsByHouse(@Param("year") int year);

    @Query(value = "SELECT student, sum(points) FROM houses_cup\n" +
            "WHERE academic_year = :#{#year}\n" +
            "AND house = :#{#house}\n" +
            "GROUP BY student", nativeQuery = true)
    List<List<Object>> getPointsPerStudent(@Param("year") int year, @Param("house") String house);

    @Query(value = "select key, array_agg(distinct value)\n" +
            "from houses_cup, jsonb_each_text(to_jsonb(houses_cup))\n" +
            "where key != 'id' \n" +
            "and key != 'explanation'\n" +
            "and key != 'date'\n" +
            "and key != 'points'\n" +
            "and key != 'student'\n" +
            "and key != 'assigning_user'\n" +
            "group by key;", nativeQuery = true)
    List<Object> getFilterables();
}
