package org.mozip.controllers.mypage2;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final MozipSaveValidator saveValidator;

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
    public String register(Model model) {

        commonFormProcess(model); // 공통 처리
        MozipForm mozipForm = new MozipForm();
        model.addAttribute("mozipForm", mozipForm);

        return "mypage2/register";
    }

    /**
     * 모임 수정
     *
     * @return
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {

        commonFormProcess(model); // 공통 처리

        Mozip mozip = infoService.get(id);


        model.addAttribute("mozip", mozip);
        MozipForm mozipForm = new ModelMapper().map(mozip, MozipForm.class);
        mozipForm.setMode("update");
        model.addAttribute("mozipForm", mozipForm);
        return "mypage2/update";
    }

    /**
     * 모임 등록 수정
     *
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid MozipForm mozipForm, Errors errors, Model model) {
        commonFormProcess(model); // 공통 처리

        saveValidator.validate(mozipForm, errors);

        String mode = mozipForm.getMode();
        if (errors.hasErrors()) {
            return mode == null ? "mypage2/register":"mypage2/update";
        }

        saveService.save(mozipForm);

        return "redirect:/mypage2/view/" + mozipForm.getId(); // 저장 완료 후 모집 보기 페이지로 이동
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Mozip mozip = infoService.get(id);
        model.addAttribute("mozip", mozip);

        return "mypage2/view";
    }

    /**
     * 양식 공통 처리
     *
     * @param model
     */
    private void commonFormProcess(Model model) {
        // 공통 자바스크립트
        String[] addScript = { "ckeditor/ckeditor", "fileUpload", "mypage/form",  };
        model.addAttribute("addScript", addScript);
    }

    /**
     * 예외 처리
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model) {

        String script = String.format("alert('%s');history.back();", e.getMessage());
        e.printStackTrace();
        model.addAttribute("script", script);

        return "commons/execute_script";
    }

}
