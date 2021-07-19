package com.salesreckon.sfm.em.domain;

import java.time.Instant;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.salesreckon.sfm.em.clients.UserClient;

import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Audited
@Getter
@Setter
@Component
@MappedSuperclass
@SuperBuilder
public class Base {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedBy
    private String createdBy;

    @Transient
    private UserRepresentation createdByUser;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @Transient
    private UserRepresentation lastModifiedByUser;

    @LastModifiedDate
    private Instant lastModifiedDate;

    private static UserClient userClient;

    @Autowired
    public void init(UserClient userClient) {
        Base.userClient = userClient;
    }

    @PostLoad
    public void postLoad() {
        this.createdByUser = userClient.get(this.createdBy).getBody();
        this.lastModifiedByUser = userClient.get(this.lastModifiedBy).getBody();
    }
}
