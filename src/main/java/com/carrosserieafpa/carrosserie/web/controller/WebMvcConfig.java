package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.UserRepository;
import com.carrosserieafpa.carrosserie.entity.User;
import com.carrosserieafpa.carrosserie.entity.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;
import java.util.List;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  public WebMvcConfig(UserRepository userRepository) {

    List<RoleEnum> adminRole = Arrays.asList(RoleEnum.USER, RoleEnum.ADMINISTRATOR);
    User adminUser = new User("admin", "carrosserie", "Admin", "ADMIN", adminRole);
    userRepository.save(adminUser);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("menu");
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/auth").setViewName("auth");
    registry.addViewController("/auth/admin").setViewName("/admin");
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources");
    }
}
