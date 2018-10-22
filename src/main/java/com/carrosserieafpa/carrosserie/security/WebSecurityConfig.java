package com.carrosserieafpa.carrosserie.security;

import com.carrosserieafpa.carrosserie.entity.User;
import com.carrosserieafpa.carrosserie.entity.enums.RoleEnum;
import com.carrosserieafpa.carrosserie.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final String adminRole = RoleEnum.ADMINISTRATOR.name();

  private final UserDetailsService userDetailsService;

  @Autowired
  public WebSecurityConfig(@Qualifier("userService") UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean(name = "passwordEncoder")
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/auth**")
        .authenticated()
        .antMatchers("/auth/admin**")
        .hasAuthority(adminRole)
        .anyRequest()
        .permitAll()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/auth")
        .failureUrl("/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .and()
        .logout()
        .invalidateHttpSession(true)
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        .and()
        .csrf()
        .and()
        .sessionManagement()
        .maximumSessions(1)
        .expiredUrl("/login");
  }
}
