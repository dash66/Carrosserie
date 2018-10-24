package com.carrosserieafpa.carrosserie;

import com.carrosserieafpa.carrosserie.web.controller.WebMvcConfig;
import com.carrosserieafpa.carrosserie.web.controller.controllerUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CarrosserieApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrosserieApplication.class, args);
    }
}
