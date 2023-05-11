package org.mozip.controllers.details;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/detail")
@RequiredArgsConstructor
public class MainDetailController {

    //상세페이지
    @GetMapping("/maindetail")
    public String index() {

        return "detail/maindetail";
    }


    //사진첩
    @GetMapping("/detailPT")
    public String scheduleDetail(){



        return "detail/detailPT";  //임시
    }

}
