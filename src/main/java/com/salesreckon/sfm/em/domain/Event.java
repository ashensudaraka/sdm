package com.salesreckon.sfm.em.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salesreckon.microservices.core.Base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@JsonIgnoreProperties("hibernateLazyInitializer")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Event extends BaseEntity {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Start is required")
    private Instant startDateTime;

    @NotNull(message = "End is required")
    private Instant endDateTime;

    @JsonIgnoreProperties("children")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParentEvent parentEvent;

    private UUID ownerId;
}
