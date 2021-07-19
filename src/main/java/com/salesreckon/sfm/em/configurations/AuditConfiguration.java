package com.salesreckon.sfm.em.configurations;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@EnableJpaAuditing
@Component
public class AuditConfiguration implements AuditorAware<String> {
 
    @Override
    public Optional<String> getCurrentAuditor() {
        if(SecurityContextHolder.getContext() instanceof SystemSecurityContext) {
            return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return Optional.of(authenticationToken.getName());
    }

}
