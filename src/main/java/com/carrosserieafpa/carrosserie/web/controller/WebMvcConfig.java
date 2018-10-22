package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.UserRepository;
import com.carrosserieafpa.carrosserie.entity.User;
import com.carrosserieafpa.carrosserie.entity.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

@EnableWebMvc
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Autowired
  public WebMvcConfig(UserRepository userRepository) {
    // Ceci n'est pas à recopier en production
    // List<RoleEnum> userRole = Collections.singletonList(RoleEnum.USER);
    // User user = new User("user", "user", "User", "USER", userRole);
    // userRepository.save(user);

    List<RoleEnum> adminRole = Arrays.asList(RoleEnum.USER, RoleEnum.ADMINISTRATOR);
    User adminUser = new User("admin", "carrosserie", "Admin", "ADMIN", adminRole);
    userRepository.save(adminUser);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/auth").setViewName("auth");
    registry.addViewController("/auth/admin").setViewName("/admin");
  }
}
