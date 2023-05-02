package org.mozip.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mozip")
public class AdminController {

    @GetMapping("/admin")
    public String layout() {
        return "admin";
    }
}
