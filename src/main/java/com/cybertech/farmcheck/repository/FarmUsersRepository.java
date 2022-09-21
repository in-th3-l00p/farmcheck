package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.FarmUsers;
import com.cybertech.farmcheck.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link com.cybertech.farmcheck.domain.FarmUsers} entity.
 */
@Repository
public interface FarmUsersRepository extends JpaRepository<FarmUsers, Long> {

    @Query("SELECT f FROM FarmUsers AS f WHERE f.user.login = :user_login AND f.farm.id = :farm_id")
    FarmUsers findFarmUsersByUserLoginAndFarmId(@Param("user_login") String userLogin, @Param("farm_id") Long farmId);
}
