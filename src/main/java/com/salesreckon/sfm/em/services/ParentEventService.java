package com.salesreckon.sfm.em.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.salesreckon.sfm.em.domain.Event;
import com.salesreckon.sfm.em.domain.ParentEvent;
import com.salesreckon.sfm.em.exceptions.BadRequestException;
import com.salesreckon.sfm.em.exceptions.EntityNotFoundException;
import com.salesreckon.sfm.em.repositories.EventRepository;
import com.salesreckon.sfm.em.repositories.ParentEventRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ParentEventService {
    private final ParentEventRepository parentEventRepository;
    private final EventRepository eventRepository;

    public ParentEvent post(@Valid ParentEvent parentEvent) {
        if (parentEvent.getChildren() == null || parentEvent.getChildren().size() == 0) {
            throw new BadRequestException("Child event is required");
        }

        var startingEvent = parentEvent.getChildren().get(0);

        if (startingEvent.getStartDateTime().isAfter(startingEvent.getEndDateTime())) {
            throw new BadRequestException("Start should be before end");
        }

        parentEvent.setChildren(null);
        parentEvent = parentEventRepository.save(parentEvent);

        var events = new ArrayList<Event>();

        while (parentEvent.getEndDateTime().isAfter(startingEvent.getEndDateTime())) {
            var newEvent = Event.builder().ownerId(parentEvent.getOwnerId()).name(parentEvent.getName()).startDateTime(startingEvent.getStartDateTime())
                    .endDateTime(startingEvent.getEndDateTime()).parentEvent(parentEvent).build();

            events.add(newEvent);

            switch (parentEvent.getRepeatType()) {
                case DAILY:
                    startingEvent.setStartDateTime(startingEvent.getStartDateTime().plusDays(1));
                    startingEvent.setEndDateTime(startingEvent.getEndDateTime().plusDays(1));
                    break;
                case WEEKLY:
                    startingEvent.setStartDateTime(startingEvent.getStartDateTime().plusWeeks(1));
                    startingEvent.setEndDateTime(startingEvent.getEndDateTime().plusWeeks(1));
                    break;
                case MONTHLY:
                    startingEvent.setStartDateTime(startingEvent.getStartDateTime().plusMonths(1));
                    startingEvent.setEndDateTime(startingEvent.getEndDateTime().plusMonths(1));
                    break;
                case YEARLY:
                    startingEvent.setStartDateTime(startingEvent.getStartDateTime().plusYears(1));
                    startingEvent.setEndDateTime(startingEvent.getEndDateTime().plusYears(1));
                    break;
            }
        }

        eventRepository.saveAll(events);

        return parentEvent;
    }

    public ParentEvent get(Long id) {
        return parentEventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
