package org.mozip.controllers.admin.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/adminCustomerController")
@RequestMapping("/admin/customer")
public class CustomerController {
    @GetMapping
    public String index() {

        return "admin/customer/index";
    }
}
