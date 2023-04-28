package org.mozip.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/test")
public class MainController {
    @GetMapping("/ex01")
    public String ex01(Model model) {
        model.addAttribute("name", "홍길동");
        return "index";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}

