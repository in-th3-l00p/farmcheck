package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends PagingAndSortingRepository<Chat, Long> {
    @Query(
        "SELECT c FROM Chat AS c " +
            "WHERE c.farm.id = :farm_id " +
            "ORDER BY c.addedDate ASC"
    )
    List<Chat> findByFarmId(@Param("farm_id") Long farmId);
}
