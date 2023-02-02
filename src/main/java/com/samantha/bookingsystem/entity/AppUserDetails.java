package com.samantha.bookingsystem.entity;

import jakarta.transaction.Transactional;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class AppUserDetails implements UserDetails {

    private String username;

    private String password;

    private List<GrantedAuthority> grantedAuthorities;

    public AppUserDetails(User user) {

        this.username = user.getUsername();

        this.password = user.getPassword();

        List<String> roles = new ArrayList<>();
        user.getRoles().forEach( role -> {
            roles.add(role.getAlias());
        });

        this.grantedAuthorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
