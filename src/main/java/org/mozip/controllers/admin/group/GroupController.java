package org.mozip.controllers.admin.group;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminGroupController")
@RequestMapping("/admin/group")
public class GroupController {
    @GetMapping
    public String index() {

        return "admin/group/index";
    }
}
