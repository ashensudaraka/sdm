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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"children", "hibernateLazyInitializer"}, allowSetters = true)
public class ParentEvent extends BaseEntity {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "End is required")
    private Instant endDateTime;

    @NotNull(message = "Repeat type is required")
    private RepeatType repeatType;

    @JsonIgnoreProperties("parentEvent")
    @OneToMany(mappedBy = "parentEvent", fetch = FetchType.LAZY)
    private List<Event> children;

    private UUID ownerId;
}
