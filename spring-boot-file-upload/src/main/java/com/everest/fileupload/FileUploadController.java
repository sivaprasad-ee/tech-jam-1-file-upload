package com.everest.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class FileUploadController {

    private static final String uploadsDir = "storage";

    @GetMapping
    public String index() {
        return "chunked";
    }

    @PostMapping("/single-upload")
    @ResponseBody
    public Map<String, String> handleSingleFileUpload(
            @RequestParam(value = "videoId", required = false) String videoId,
            @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if(videoId==null || videoId.isBlank()) {
            videoId = UUID.randomUUID().toString();
        }
        String originalFilename = videoId + "-" + multipartFile.getOriginalFilename();
        File targetFile = new File(uploadsDir, originalFilename);
        if(!targetFile.exists()){
            targetFile.createNewFile();
        }
        Files.write(
                Paths.get(targetFile.getAbsolutePath()),
                multipartFile.getBytes(),
                StandardOpenOption.APPEND);

        return Map.of("videoId", videoId);
    }

}
