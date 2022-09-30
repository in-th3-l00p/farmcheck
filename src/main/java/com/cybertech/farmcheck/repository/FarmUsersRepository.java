package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.FarmUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link com.cybertech.farmcheck.domain.FarmUsers} entity.
 */
@Repository
public interface FarmUsersRepository extends JpaRepository<FarmUsers, Long> {

    @Query("SELECT f FROM FarmUsers AS f WHERE f.user.login = :user_login AND f.farm.id = :farm_id")
    Optional<FarmUsers> findFarmUsersByUserLoginAndFarmId(@Param("user_login") String userLogin, @Param("farm_id") Long farmId);
}
