package com.pandasaza.base.uploader;

import com.pandasaza.base.utils.Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UploadController {

    private final Uploader uploader;
    @PostMapping("/api/v1/upload")
    public String upload(@RequestParam("data") MultipartFile file) throws IOException {
        log.info("file: {}",file);
        return uploader.upload(file, "static");
    }

}
