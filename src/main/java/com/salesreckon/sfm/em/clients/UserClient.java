package com.salesreckon.sfm.em.clients;

import com.salesreckon.sfm.em.domain.UserRepresentation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserClient", url = "http://um/users")
public interface UserClient {
    @Cacheable("users")
    @GetMapping("{id}")
    public ResponseEntity<UserRepresentation> get(@PathVariable String id);
}
