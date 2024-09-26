package com.emazon.cart.infrastructure.adapters.in.security.filters;

import com.emazon.cart.domain.exceptions.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.emazon.cart.infrastructure.utils.constants.SecurityConstant.TOKEN_PREFIX;

@Component
@AllArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {
  private final UserDetailsService userDetailsService;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (token != null && token.startsWith(TOKEN_PREFIX)) {
      try{
        UserDetails userDetails = userDetailsService.loadUserByUsername(token);

        if(userDetails != null){
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
          );

          SecurityContext context = SecurityContextHolder.getContext();
          context.setAuthentication(authentication);
        }
      }catch (BusinessException e){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(e.getMessage());
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}
