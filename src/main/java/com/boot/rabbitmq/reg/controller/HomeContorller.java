package com.boot.rabbitmq.reg.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeContorller {
    /**
     *
     * @return
     */
    @GetMapping("/login")
    public String login(){
        System.out.println("aaaaaaaaaaa");
       return "login";
    }

    /**
     *
     * @return
     */
    @GetMapping("/main")
    public String main(){
        return "index";
    }
}
