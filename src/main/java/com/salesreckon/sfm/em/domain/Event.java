package com.salesreckon.sfm.em.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@JsonIgnoreProperties("hibernateLazyInitializer")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Event extends Base{
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Start is required")
    private LocalDateTime startDateTime;
    
    @NotNull(message = "End is required")
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private ParentEvent parentEvent;

    private Long ownerId;
}