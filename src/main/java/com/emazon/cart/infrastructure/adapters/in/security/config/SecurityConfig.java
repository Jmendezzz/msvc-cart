package com.emazon.cart.infrastructure.adapters.in.security.config;


import com.emazon.cart.infrastructure.adapters.in.security.filters.CustomAccessDeniedHandler;
import com.emazon.cart.infrastructure.adapters.in.security.filters.JwtAuthenticationEntryPoint;
import com.emazon.cart.infrastructure.adapters.in.security.filters.JwtTokenValidatorFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.emazon.cart.infrastructure.utils.constants.SecurityConstant.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
  private final JwtTokenValidatorFilter jwtTokenValidationFilter;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizeRequests -> {
              authorizeRequests.requestMatchers(ERROR_URL).permitAll();
              authorizeRequests.requestMatchers(SWAGGER_UI_URL).permitAll();
              authorizeRequests.requestMatchers(SWAGGER_API_DOCS_URL).permitAll();
              authorizeRequests.anyRequest().authenticated();
            })
            .exceptionHandling(exceptionHandling ->{
              exceptionHandling.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
              exceptionHandling.accessDeniedHandler(new CustomAccessDeniedHandler());
            })
            .addFilterBefore(jwtTokenValidationFilter, BasicAuthenticationFilter.class)
            .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
