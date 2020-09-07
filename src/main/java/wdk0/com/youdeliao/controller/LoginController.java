package wdk0.com.youdeliao.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")

    public String login(){
        return "forward:/../templates/account/login.html";
    }
}
