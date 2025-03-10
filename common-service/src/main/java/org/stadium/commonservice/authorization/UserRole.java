package org.stadium.commonservice.authorization;

import lombok.Getter;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Getter
public enum UserRole {

    GUEST(Set.of(Permission.EVENT_VIEW)), 
    USER(Set.of(Permission.USER_READ,
        Permission.TICKET_VIEW,
        Permission.TICKET_BOOK)),
    AUDITOR(Set.of(
        Permission.USER_READ,
        Permission.TICKET_VIEW,
        Permission.EVENT_VIEW,
        Permission.REPORT_GENERATE)),
    ADMIN(Permission.ALL_PERMISSIONS);

    private final Set<Permission> permissions;

    UserRole(Set<Permission> permissions){
        this.permissions = permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return permissions.stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getAuthority()))
            .collect(Collectors.toSet());

    }
}
