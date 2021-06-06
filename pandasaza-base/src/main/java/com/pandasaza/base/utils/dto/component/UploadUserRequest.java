package com.pandasaza.base.utils.dto.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UploadUserRequest {
    private List<MultipartFile> profileImageList;

}
