package com.salesreckon.sfm.em.controllers;

import com.salesreckon.sfm.em.domain.ParentEvent;
import com.salesreckon.sfm.em.services.ParentEventService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("parent-events")
public class ParentEventController {
    private final ParentEventService parentEventService;

    @PostMapping
    public ResponseEntity<ParentEvent> post(@RequestBody ParentEvent parentEvent) {
        return ResponseEntity.created(null).body(parentEventService.post(parentEvent));
    }

    @GetMapping("{id}")
    public ResponseEntity<ParentEvent> get(@PathVariable Long id) {
        return ResponseEntity.ok(parentEventService.get(id));
    }
}
