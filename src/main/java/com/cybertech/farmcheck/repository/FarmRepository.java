package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Farm;
import com.cybertech.farmcheck.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link com.cybertech.farmcheck.domain.Farm} entity.
 */
@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

}
