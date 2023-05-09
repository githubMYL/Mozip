package org.mozip.controllers.files;

import lombok.RequiredArgsConstructor;
import org.mozip.entities.FileInfo;
import org.mozip.models.file.FileInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class FileViewController {

    private final FileInfoService infoService;

    @GetMapping("/file/view/{fileNo}")
    public String view(@PathVariable Long fileNo, Model model) {

        try {
            FileInfo fileInfo = infoService.get(fileNo);
            model.addAttribute("fileURL", fileInfo.getFileURL());
        } catch (Exception e) {
            model.addAttribute("script", "alert('" + e.getMessage() + "');mozip.popup.close();");
            return "commons/execute_script";
        }

        return "file/view";
    }
}
