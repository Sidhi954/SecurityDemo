package org.example;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @GetMapping("/greet")
    public String displayMessage(){
        return "Hello World!";
    }

    @PreAuthorize("hasRole('USER)")
    @GetMapping("/user")
    public String userEndPoint(){
        return "Hello User";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminEndPoint(){
        return "Hello Admin";
    }
}
