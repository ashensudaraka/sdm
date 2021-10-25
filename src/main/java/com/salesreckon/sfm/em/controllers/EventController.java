package com.salesreckon.sfm.em.controllers;

import com.salesreckon.microservices.core.Base.BaseController;
import com.salesreckon.sfm.em.domain.Event;
import com.salesreckon.sfm.em.repositories.EventRepository;
import com.salesreckon.sfm.em.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("events")
public class EventController extends BaseController<Event, EventRepository, EventService> {
    @GetMapping("owner/{id}")
    public ResponseEntity<Page<Event>> listForOwnerAndMonth(@PathVariable UUID id,
                                                            @RequestParam Instant monthStartDateTime, @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "DESC") Direction direction,
                                                            @RequestParam(defaultValue = "lastModifiedBy") String sortBy) {
        return ResponseEntity.ok(baseService.listForOwnerAndMonth(id, monthStartDateTime,
                PageRequest.of(page, size, direction, sortBy)));
    }

    @GetMapping("parent-event/{id}")
    public ResponseEntity<Page<Event>> listForParent(@PathVariable UUID id, @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "DESC") Direction direction,
                                                     @RequestParam(defaultValue = "lastModifiedBy") String sortBy) {
        return ResponseEntity.ok(baseService.listForParent(id, PageRequest.of(page, size, direction, sortBy)));
    }
}
