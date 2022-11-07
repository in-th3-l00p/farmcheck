package com.cybertech.farmcheck.repository;

import com.cybertech.farmcheck.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    @Query(
        "SELECT m FROM Message AS m " +
            "WHERE m.chat.id = :chat_id " +
            "ORDER BY m.dateTime ASC"
    )
    List<Message> findByChatId(@Param("chat_id") Long chatId);
}
