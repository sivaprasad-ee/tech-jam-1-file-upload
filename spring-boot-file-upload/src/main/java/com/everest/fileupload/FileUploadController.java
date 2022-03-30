package com.everest.fileupload;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;

@Controller
public class FileUploadController {

    private static final String uploadsDir = "storage";

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/streaming-upload")
    public String handleStreamingFileUpload(HttpServletRequest request,
                                                  RedirectAttributes redirectAttributes) throws Exception {
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iterStream = upload.getItemIterator(request);
        while (iterStream.hasNext()) {
            FileItemStream item = iterStream.next();
            String name = item.getName();
            InputStream stream = item.openStream();
            if (name != null && !name.isBlank() && !item.isFormField()) {
                // Process the InputStream
                File targetFile = new File(uploadsDir, name);
                FileUtils.copyInputStreamToFile(stream, targetFile);
            } else {
                String formFieldValue = Streams.asString(stream);
                System.out.println("Field Name: "+ item.getFieldName() + ", value: "+ formFieldValue);
            }
        }

        redirectAttributes.addFlashAttribute("msg", "File(s) uploaded successfully");
        return "redirect:/";
    }
}