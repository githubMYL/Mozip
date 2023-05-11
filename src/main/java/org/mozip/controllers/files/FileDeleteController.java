package org.mozip.controllers.files;

import lombok.RequiredArgsConstructor;
import org.mozip.models.file.FileDeleteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/file/delete")
@RequiredArgsConstructor
public class FileDeleteController {
    private final FileDeleteService deleteService;

    @RequestMapping("/{fileNo}")
    public String delete(@PathVariable Long fileNo, Model model) {
        String script = null;
        try {
            deleteService.delete(fileNo);

            script = String.format("if (typeof parent.fileDeleteCallback == 'function') parent.fileDeleteCallback(%d);", fileNo);

        } catch (Exception e) {
            e.printStackTrace();
            script = String.format("alert('%s');", e.getMessage());
        }
        model.addAttribute("script", script);
        return "commons/execute_script";
    }


    @ResponseBody
    @GetMapping("/gid/{gid}")
    public void deleteByGid(@PathVariable String gid) {
        System.out.println("유입되나요?");
        deleteService.delete(gid);
    }
}
