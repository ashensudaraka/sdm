package com.salesreckon.sfm.em.controllers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.salesreckon.sfm.em.domain.Event;
import com.salesreckon.sfm.em.services.EventService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("events")
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> post(@RequestBody Event event) {
        return ResponseEntity.created(null).body(eventService.post(event));
    }

    @GetMapping("{id}")
    public ResponseEntity<Event> get(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.get(id));
    }

    @GetMapping("owner/{id}")
    public ResponseEntity<Page<Event>> listForOwnerAndMonth(@PathVariable Long id,
            @RequestParam Instant monthStartDateTime, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "DESC") Direction direction,
            @RequestParam(defaultValue = "lastModifiedBy") String sortBy) {
        return ResponseEntity.ok(eventService.listForOwnerAndMonth(id, monthStartDateTime,
                PageRequest.of(page, size, direction, sortBy)));
    }

    @GetMapping("parent-event/{id}")
    public ResponseEntity<Page<Event>> listForParent(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "DESC") Direction direction,
            @RequestParam(defaultValue = "lastModifiedBy") String sortBy) {
        return ResponseEntity.ok(eventService.listForParent(id, PageRequest.of(page, size, direction, sortBy)));
    }
}
