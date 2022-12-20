package com.maat.repository;

import com.maat.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository adaptation for the House entity.
 * @author Brand Hauser
 */
@Repository
public interface HouseRepository extends JpaRepository<House, Integer> {
    House findHouseByName(String name);

    void deleteHouseByName(String name);

    boolean existsByName(String name);

    @Query(value = "select key, array_agg(distinct value)\n" +
            "from houses, jsonb_each_text(to_jsonb(houses))\n" +
            "where key != 'population' \n" +
            "and key != 'housekeeper'\n" +
            "group by key;", nativeQuery = true)
    List<Object> getFilterables();

}
