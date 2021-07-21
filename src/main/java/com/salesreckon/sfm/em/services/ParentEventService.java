package com.salesreckon.sfm.em.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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

    private static ZoneId zoneId = ZoneId.of("Asia/Colombo");

    public static Instant addDay(Instant instant) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
        zdt = zdt.plusDays(1);
        return zdt.toInstant();
    }

    public static Instant addWeek(Instant instant) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
        zdt = zdt.plusWeeks(1); 
        return zdt.toInstant();
    }

    public static Instant addMonth(Instant instant) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
        zdt = zdt.plusMonths(1);
        return zdt.toInstant();
    }

    public static Instant addYear(Instant instant) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
        zdt = zdt.plusYears(1);
        return zdt.toInstant();
    }

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
            var newEvent = Event.builder().ownerId(parentEvent.getOwnerId()).name(parentEvent.getName())
                    .startDateTime(startingEvent.getStartDateTime()).endDateTime(startingEvent.getEndDateTime())
                    .parentEvent(parentEvent).build();

            events.add(newEvent);

            switch (parentEvent.getRepeatType()) {
                case DAILY:
                    startingEvent.setStartDateTime(addDay(startingEvent.getStartDateTime()));
                    startingEvent.setEndDateTime(addDay(startingEvent.getEndDateTime()));
                    break;
                case WEEKLY:
                    startingEvent.setStartDateTime(addWeek(startingEvent.getStartDateTime()));
                    startingEvent.setEndDateTime(addWeek(startingEvent.getEndDateTime()));
                    break;
                case MONTHLY:
                    startingEvent.setStartDateTime(addMonth(startingEvent.getStartDateTime()));
                    startingEvent.setEndDateTime(addMonth(startingEvent.getEndDateTime()));
                    break;
                case YEARLY:
                    startingEvent.setStartDateTime(addYear(startingEvent.getStartDateTime()));
                    startingEvent.setEndDateTime(addYear(startingEvent.getEndDateTime()));
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
