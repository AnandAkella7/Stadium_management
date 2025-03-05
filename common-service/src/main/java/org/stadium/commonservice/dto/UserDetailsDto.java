package org.stadium.commonservice.dto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

public record UserDetailsDto(String email, String password, Set<String> authorities) 
{
    public UserDetails toUserDetails()
    {
        return new User(email, password, authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet()));
    }

    private static class User extends org.springframework.security.core.userdetails.User{

        public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
            super(username, password, authorities);
        }
    }

}
