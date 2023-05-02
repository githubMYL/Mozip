package org.mozip.controllers.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mozip")
public class MainController {
    @GetMapping
    public String main(){
        return "mozip";
    }
}
