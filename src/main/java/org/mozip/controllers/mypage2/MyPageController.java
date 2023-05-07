package org.mozip.controllers.mypage2;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mozip.controllers.entities.Mozip;
import org.mozip.models.mozip.MozipDeleteService;
import org.mozip.models.mozip.MozipInfoService;
import org.mozip.models.mozip.MozipListService;
import org.mozip.models.mozip.MozipSaveService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller("mypageController2")
@RequestMapping("/mypage2")
@RequiredArgsConstructor
public class MyPageController {

    private final MozipSaveService saveService;
    private final MozipInfoService infoService;
    private final MozipListService listService;
    private final MozipDeleteService deleteService;

    /**
     * 모임 마이페이지 - 목록
     *
     * @return
     */
    @GetMapping
    public String index(@ModelAttribute MozipSearch search, Model model) {

        Page<Mozip> data = listService.gets(search);

        return "mypage2/index";
    }

    /**
     * 모임 생성
     *
     * @return
     */
    @GetMapping("/add")
    public String register() {

        return "mypage2/register";
    }

    /**
     * 모임 수정
     *
     * @return
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Mozip mozip = infoService.get(id);
        model.addAttribute("mozip", mozip);

        return "mypage2/update";
    }

    /**
     * 모임 등록 수정
     *
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid MozipForm mozipForm, Errors errors, Model model) {

        saveService.save(mozipForm);

        return "redirect/view/" + mozipForm.getId(); // 저장 완료 후 모집 보기 페이지로 이동
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Mozip mozip = infoService.get(id);
        model.addAttribute("data", mozip);

        return "mypage2/view";
    }

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model) {

        String script = String.format("alert('%s');history.back();", e.getMessage());
        e.printStackTrace();
        model.addAttribute("script", script);
        
        return "commons/execute_script";
    }

}
