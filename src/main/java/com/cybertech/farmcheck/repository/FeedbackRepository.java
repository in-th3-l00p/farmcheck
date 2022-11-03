package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Feedback;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedbackRepository extends PagingAndSortingRepository<Feedback, Long> {

}
