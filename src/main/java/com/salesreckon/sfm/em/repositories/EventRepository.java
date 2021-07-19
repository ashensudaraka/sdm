package com.salesreckon.sfm.em.repositories;

import java.time.LocalDateTime;

import com.salesreckon.sfm.em.domain.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByOwnerIdAndStartDateTimeBetween(Long id, LocalDateTime monthStartDateTime, LocalDateTime monthEndDateTime, Pageable pageable);
    Page<Event> findByParentEventId(Long id, Pageable pageable);
}
