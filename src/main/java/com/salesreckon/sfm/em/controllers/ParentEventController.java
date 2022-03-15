package com.salesreckon.sfm.em.controllers;

import com.salesreckon.microservices.core.Base.BaseController;
import com.salesreckon.sfm.em.domain.ParentEvent;
import com.salesreckon.sfm.em.repositories.ParentEventRepository;
import com.salesreckon.sfm.em.services.ParentEventService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parent-events")
public class ParentEventController extends BaseController<ParentEvent, ParentEventRepository, ParentEventService> {
    public ParentEventController() {
        super("parent_event");
    }
}
