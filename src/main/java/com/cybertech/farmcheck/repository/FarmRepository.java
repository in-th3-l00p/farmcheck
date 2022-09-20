package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link com.cybertech.farmcheck.domain.Farm} entity.
 */
@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    @Query("SELECT f.farm FROM FarmUsers AS f WHERE f.user.login = :user_login")
    List<Farm> findAllByUserLogin(@Param("user_login") String userLogin);

    @Query("SELECT f.user FROM FarmUsers AS f WHERE f.farm.id = :farm_id")
    List<User> findUsersById(@Param("farm_id") Long farmId);
}
