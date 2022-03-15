package com.salesreckon.sfm.em.repositories;

import com.salesreckon.microservices.core.Base.BaseRepository;
import com.salesreckon.sfm.em.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface EventRepository extends BaseRepository<Event> {
    Page<Event> findByOwnerIdAndStartDateTimeBetween(UUID id, Instant monthStartDateTime, Instant monthEndDateTime, Pageable pageable);

    Page<Event> findByParentEventId(UUID id, Pageable pageable);
}
