package com.mif10_g14_2024.mif10_g14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class Mif10G14Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Mif10G14Application.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(Mif10G14Application.class, args);
    }
}
