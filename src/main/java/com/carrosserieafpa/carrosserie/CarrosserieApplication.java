package com.carrosserieafpa.carrosserie;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CarrosserieApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CarrosserieApplication.class, args);
    }
}
