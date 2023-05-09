package org.mozip.controllers.files;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mozip.entities.FileInfo;
import org.mozip.models.file.FileInfoService;
import org.mozip.models.file.FileNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequiredArgsConstructor
public class FileDownloadController {
    private final FileInfoService infoService;

    @GetMapping("/file/download/{fileNo}")
    public void download(@PathVariable Long fileNo, HttpServletResponse response) {
        FileInfo fileInfo = null;
        File file = null;
        try {
            fileInfo = infoService.get(fileNo);

            file = new File(fileInfo.getFilePath());

            if (!file.exists()) {
                throw new FileNotFoundException();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            OutputStream out = response.getOutputStream();

            String fileName = new String(fileInfo.getOriginalFilename().getBytes(), "ISO8859_1");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-Type", "application/octet-stream");
            response.setIntHeader("Expires", 0);
            response.setHeader("Cache-Control", "must-revalidate");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Length", String.valueOf(file.length()));

            while(bis.available() > 0) {
                out.write(bis.read());
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
