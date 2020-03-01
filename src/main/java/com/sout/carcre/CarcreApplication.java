package com.sout.carcre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class CarcreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarcreApplication.class, args);
    }

}
