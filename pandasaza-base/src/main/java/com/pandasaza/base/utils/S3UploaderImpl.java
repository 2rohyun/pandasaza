package com.pandasaza.base.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pandasaza.base.utils.dto.component.S3UploadComponent;
import com.pandasaza.base.utils.dto.component.UploadUserRequestForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3UploaderImpl implements Uploader {

    private final static String TEMP_FILE_PATH = "src/main/resources/";

    private final AmazonS3Client amazonS3Client;

    private final S3UploadComponent s3UploadComponent;


    public List<String> upload(List<MultipartFile> imageList, String dirName) throws IOException {
        List<File> convertedFileList = convert(imageList);
        return uploadImages(convertedFileList, dirName);
    }

    private List<String> uploadImages(List<File> uploadFileList, String dirName) {
        List<String> uploadImageUrlList = new ArrayList<>();
        uploadFileList.stream()
                .forEach(uploadFile -> {
                    String fileName = dirName + "/" + uploadFile.getName();
                    String uploadImageUrl = putS3(uploadFile, fileName);
                    removeNewFile(uploadFile);
                    uploadImageUrlList.add(uploadImageUrl);
                });
        return uploadImageUrlList;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(s3UploadComponent.getBucket(), fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(s3UploadComponent.getBucket(), fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            return;
        }
        log.info("임시 파일이 삭제 되지 못했습니다. 파일 이름: {}", targetFile.getName());
    }

    private List<File> convert(List<MultipartFile> imageList) throws IOException {

        List<File> convertFileList = new ArrayList<>();
        imageList.stream()
                .forEach(uploadFile ->{
                    File convertFile = new File(TEMP_FILE_PATH + uploadFile.getOriginalFilename());
                    if(convertFile.exists()){
                        convertFile.delete();
                    }
                    System.out.println(convertFile);
                    try {
                        if (convertFile.createNewFile()) {//이미 파일이 존재하면 return false
                            System.out.println("여기확인");
                            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                                fos.write(uploadFile.getBytes());

                            }
                            convertFileList.add(convertFile);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //throw new IllegalArgumentException(String.format("파일 변환이 실패했습니다."));
                });
        return convertFileList;

    }

}

