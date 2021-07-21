package com.salesreckon.sfm.em.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"children", "hibernateLazyInitializer"}, allowSetters = true)
public class ParentEvent extends Base{
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "End is required")
    private Instant endDateTime;

    @NotNull(message = "Repeat type is required")
    private RepeatType repeatType;

    @OneToMany(mappedBy = "parentEvent", fetch = FetchType.LAZY)
    private List<Event> children;    

    private Long ownerId;
}
