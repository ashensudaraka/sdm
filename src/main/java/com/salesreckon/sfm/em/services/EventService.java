package com.salesreckon.sfm.em.services;

import java.time.LocalDateTime;
import java.time.Month;

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

    public Page<Event> listForOwnerAndMonth(Long id, LocalDateTime monthStartDateTime, Pageable pageable) {
        return eventRepository.findByOwnerIdAndStartDateTimeBetween(id, monthStartDateTime, monthStartDateTime.plusMonths(1), pageable);
    }

    public Page<Event> listForParent(Long id, Pageable pageable) {
        return eventRepository.findByParentEventId(id, pageable);
    }
}
