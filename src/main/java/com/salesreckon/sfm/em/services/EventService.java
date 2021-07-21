package com.salesreckon.sfm.em.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import com.salesreckon.sfm.em.domain.Event;
import com.salesreckon.sfm.em.exceptions.EntityNotFoundException;
import com.salesreckon.sfm.em.repositories.EventRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;

    public Event post(@Valid Event event) {
        return eventRepository.save(event);
    }

    public Event get(Long id) {
        return eventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Event> listForOwnerAndMonth(Long id, Instant monthStartDateTime, Pageable pageable) {
        
        return eventRepository.findByOwnerIdAndStartDateTimeBetween(id, monthStartDateTime, ParentEventService.addMonth(monthStartDateTime), pageable);
    }

    public Page<Event> listForParent(Long id, Pageable pageable) {
        return eventRepository.findByParentEventId(id, pageable);
    }
}
