package com.pandasaza.base.utils;

import com.pandasaza.base.utils.dto.component.UploadUserRequestForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface Uploader {

    List<String> upload(UploadUserRequestForm multipartFile, String dirName) throws IOException;

}