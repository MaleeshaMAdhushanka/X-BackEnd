package lk.ecommerce.zeetradexbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "WelCome to treading platform";
    }
    @GetMapping("/api")
    public String secure(){
        return "WelCome to treading platform Secure";
    }
}
