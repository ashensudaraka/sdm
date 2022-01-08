package com.salesreckon.sfm.em.services;

import com.salesreckon.microservices.core.Base.BaseService;
import com.salesreckon.sfm.em.domain.Event;
import com.salesreckon.sfm.em.exceptions.EntityNotFoundException;
import com.salesreckon.sfm.em.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.Instant;
import java.util.UUID;

@Service
public class EventService extends BaseService<Event, EventRepository> {

    public EventService() {
        super("event", Event.class);
    }

    public Page<Event> listForOwnerAndMonth(UUID id, Instant monthStartDateTime, Pageable pageable) {

        return baseRepository.findByOwnerIdAndStartDateTimeBetween(id, monthStartDateTime, ParentEventService.addMonth(monthStartDateTime), pageable);
    }

    public Page<Event> listForParent(UUID id, Pageable pageable) {
        return baseRepository.findByParentEventId(id, pageable);
    }
}
