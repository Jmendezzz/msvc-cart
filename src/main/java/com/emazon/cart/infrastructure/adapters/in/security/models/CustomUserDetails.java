package com.emazon.cart.infrastructure.adapters.in.security.models;

import com.emazon.cart.domain.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static com.emazon.cart.infrastructure.utils.constants.SecurityConstant.ROLE_PREFIX;

public class CustomUserDetails implements UserDetails {
  private final Long id;
  private final String email;
  private final Collection<? extends GrantedAuthority> authorities;

  public CustomUserDetails(User user) {
    this.id = user.id();
    this.email = user.email();
    this.authorities = mapRolesToAuthorities(user.role());
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {
    return List.of(
            new SimpleGrantedAuthority(ROLE_PREFIX + role)
    );
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return email;
  }
  public Long getId() {
    return id;
  }
}
