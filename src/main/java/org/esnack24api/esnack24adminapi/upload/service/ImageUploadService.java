package org.esnack24api.esnack24adminapi.upload.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.esnack24api.esnack24adminapi.upload.util.S3Uploader;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageUploadService {

    private final S3Uploader s3Uploader;

    public String uploadBase64File(String base64File) throws IOException {
        // 이미지 파일이 없는 경우 null 또는 기존 파일명 반환
        if (base64File == null || base64File.isEmpty()) {
            return null;
        }

        String base64Data = base64File;
        String fileExtension = "";

        // data:image/...;base64, 접두사 처리
        if (base64Data.contains(",")) {
            String[] parts = base64Data.split(",");
            String mimeType = parts[0].split(";")[0].split(":")[1];
            fileExtension = "." + mimeType.split("/")[1];
            base64Data = parts[1];
        }

        // 공백 및 줄바꿈 문자 제거
        base64Data = base64Data.replaceAll("\\s+", "");

        // UUID 파일명 생성
        String savedFileName = UUID.randomUUID() + fileExtension;
        byte[] fileBytes = Base64.getDecoder().decode(base64Data);

        // 로컬 저장 경로 설정
        String uploadPath = "C:\\snack\\demo\\";
        Path localPath = Paths.get(uploadPath + savedFileName);
        Path localthumbnailPath = Paths.get(uploadPath + "s_" + savedFileName);

        // 임시 파일 생성 (UUID 파일명 그대로 사용)
        Path tempFile = Files.createTempFile("temp_", fileExtension);
        Files.write(tempFile, fileBytes);

        try {
            // S3에 UUID 파일명으로 업로드
            s3Uploader.upload(tempFile.toString(), savedFileName);

            // 로컬 폴더에 이미지 저장
            Files.write(localPath, fileBytes);
            Thumbnails.of(localPath.toFile())
                    .size(200, 133)
                    .toFile(localthumbnailPath.toFile());

            return savedFileName;
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}