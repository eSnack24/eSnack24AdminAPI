package org.esnack24api.esnack24adminapi.upload.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;


@Component
@RequiredArgsConstructor
@Log4j2
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${AWS_BUCKET}")
    public String bucket;

    // S3 파일 업로드하기
    public String upload(String filePath) {

        File targetFile = new File(filePath);

        String uploadImageUrl = putS3(targetFile, targetFile.getName()); // S3로 업로드
        removeOriginalFile(targetFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        // 원본 이미지 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket, "product/" + fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        // 썸네일 이미지 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket, "product/s_" + fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, "product/" + fileName).toString();
    }

    // S3 업로드 후 원본(로컬) 파일 삭제
    private void removeOriginalFile(File targetFile) {
        if (targetFile.exists() && targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("fail to remove");
    }

    // S3에 업로드 된 파일 삭제
    public void removeS3File(String fileName){
        final DeleteObjectRequest deleteObjectRequest = new
                DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }
}
