package com.mycompany.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@SpringBootApplication
@RestController
public class StarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class,args);
    }


    @GetMapping("permitAll")
    public String permitAll() {
        return "PermitAll";
    }

    @GetMapping("authenticated")
    public String authenticated() {
        return "authenticated";
    }

    @GetMapping("withRole/normal")
    public String withRole() {
        return "withRole: normal";
    }


}
