package com.maat.repository;

import com.maat.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository adaptation for the User entity.
 * @author Brand Hauser
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor {

    @Override
    @EntityGraph(value = "graph.Users.house.id", type = EntityGraph.EntityGraphType.FETCH)
    List<User> findAll();

    @Query(value = "select key, array_agg(distinct value)\n" +
            "from users, jsonb_each_text(to_jsonb(users))\n" +
            "where key != 'id_number' \n" +
            "and key != 'first_name'\n" +
            "and key != 'last_name'\n" +
            "and key != 'email'\n" +
            "group by key;", nativeQuery = true)
    List<Object> getFilterables();

    @EntityGraph(value = "graph.Users.house.id", type = EntityGraph.EntityGraphType.FETCH)
    User findByIdNumber(int id);

    @Modifying
    @Query(value = "delete from user_roles where user_id = :#{#idNumber}", nativeQuery = true)
    void deleteUserRoles(@Param("idNumber") int idNumber);

    void deleteByIdNumber(int id);
}
