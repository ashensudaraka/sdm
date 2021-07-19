package com.salesreckon.sfm.em.repositories;

import com.salesreckon.sfm.em.domain.ParentEvent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentEventRepository extends JpaRepository<ParentEvent, Long> {
    
}
