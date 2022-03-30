package com.everest.fileupload;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileUploadController {

    private static final String uploadsDir = "storage";

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/single-upload")
    public String handleSingleFileUpload(@RequestParam("singleFile") MultipartFile multipartFile,
                                         RedirectAttributes redirectAttributes) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        String originalFilename = multipartFile.getOriginalFilename();
        File targetFile = new File(uploadsDir, originalFilename);
        FileUtils.copyInputStreamToFile(inputStream, targetFile);
        redirectAttributes.addFlashAttribute("msg", "Single file uploaded successfully");
        return "redirect:/";
    }

    @PostMapping("/multiple-upload")
    public String handleMultipleFilesUpload(@RequestParam("file1") MultipartFile multipartFile1,
                                            @RequestParam("file2") MultipartFile multipartFile2,
                                         RedirectAttributes redirectAttributes) throws IOException {
        File targetFile1 = new File(uploadsDir, multipartFile1.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile1.getInputStream(), targetFile1);

        File targetFile2 = new File(uploadsDir, multipartFile2.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile2.getInputStream(), targetFile2);

        redirectAttributes.addFlashAttribute("msg", "Multiple files uploaded successfully");
        return "redirect:/";
    }

}
