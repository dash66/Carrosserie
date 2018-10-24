/*package com.carrosserieafpa.carrosserie.security;

import com.carrosserieafpa.carrosserie.entity.enums.RoleEnum;
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
*/
 // private static final String[] staticResources = {
 //  "/webjars/**",
 //   "/css/**",
 //   "/js/**",
 //   "/images/**",
 //   "/",
 //   "/fonts",
 //   "/resources/static/images/**",
 //   "/static/**",
  //  "/static/images/**",
 //   "/images/**",
 //   "/resources/**",
 //  "**/images/"

 // };

/*
  @Override
  protected void configure(HttpSecurity http) throws Exception {


    http.authorizeRequests()
        .antMatchers(staticResources)
        .permitAll()
        .antMatchers("/resources/**", "/static/**", "/images/**")
        .permitAll()
        .anyRequest()
        .authenticated();

    http.authorizeRequests()
        .antMatchers("/auth")
        .authenticated()
        .antMatchers("/auth/admin**")
        .hasAuthority(adminRole)
        .anyRequest()
        .permitAll()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/admin")
        .failureUrl("/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .and()
        .logout()
        .invalidateHttpSession(true)
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .and()
        .csrf()
        .and()
        .sessionManagement()
        .maximumSessions(1)
        .expiredUrl("/");
  }
}
*/