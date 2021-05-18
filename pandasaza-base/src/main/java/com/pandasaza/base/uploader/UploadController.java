package com.pandasaza.base.uploader;

import com.pandasaza.base.utils.Uploader;
import com.pandasaza.base.utils.dto.component.UploadUserRequestForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UploadController {

    private final Uploader uploader;
    @PostMapping("/api/v1/upload")
    public List<String> upload(@ModelAttribute UploadUserRequestForm uploadUserRequestForm) throws IOException {
        log.info("file: {}",uploadUserRequestForm);
        uploadUserRequestForm.getUploadUserRequest().getProfileImageList().stream()
            .forEach(uploadFile ->{
                System.out.println(uploadFile.getOriginalFilename());
            });

        return uploader.upload(uploadUserRequestForm, "static");
    }

}
