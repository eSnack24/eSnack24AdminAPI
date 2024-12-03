package org.esnack24api.esnack24adminapi.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.common.page.PageRequest;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.product.dto.*;
import org.esnack24api.esnack24adminapi.product.repository.ProductAllergyRepository;
import org.esnack24api.esnack24adminapi.product.repository.ProductRepository;
import org.esnack24api.esnack24adminapi.upload.util.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductAllergyService productAllergyService;
    private final S3Uploader s3Uploader;

    private String saveBase64File(String base64File) throws IOException {
        String base64Data = base64File;
        String fileExtension = "";

        if (base64Data.contains(",")) {
            String[] parts = base64Data.split(",");
            String mimeType = parts[0].split(";")[0].split(":")[1];
            fileExtension = "." + mimeType.split("/")[1];
            base64Data = parts[1];
        }

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

    // 상품 리스트 조회
    @Transactional(readOnly = true)
    public PageResponseDTO<ProductListDTO> getProductList(PageRequestDTO pageRequestDTO) {
        log.info("getProductList");

        return productRepository.listProductAll(pageRequestDTO);
    }

    // 상품-알러지 리스트 조회
    @Transactional(readOnly = true)
    public PageResponseDTO<ProductAllergyListDTO> getProductAllergyList(PageRequestDTO pageRequestDTO) {
        log.info("getProductAllergy-List");

        return productRepository.listProductAllergyInfo(pageRequestDTO);
    }

    // 상품 상세 조회
    @Transactional(readOnly = true)
    public ProductDetailDTO getProductOne(Long pno) {
        log.info("getProductOne");

        return productRepository.getProductOne(pno);
    }

    // 상품 추가
    @Transactional
    public String addProduct(ProductAddDTO productAddDTO) {
        try {
            // Base64 파일을 S3에 업로드하고 파일명 받기
            String savedFileName = saveBase64File(productAddDTO.getPfilename());

            // 제품 엔티티 생성
            ProductEntity productEntity = ProductEntity.builder()
                    .ptitle_ko(productAddDTO.getPtitle_ko())
                    .pcontent_ko(productAddDTO.getPcontent_ko())
                    .pcategory_ko(productAddDTO.getPcategory_ko())
                    .price(productAddDTO.getPrice())
                    .pqty(productAddDTO.getPqty())
                    .pfilename(savedFileName)
                    .build();

            // 데이터베이스 저장
            ProductEntity savedProduct = productRepository.save(productEntity);

            // 알레르기 정보 연결
            productAllergyService.saveProductAllergies(savedProduct, productAddDTO.getAllergySelectList());

            return "상품이 등록되었습니다.";
        } catch (Exception e) {
            log.error("상품 등록 실패", e);
            throw new RuntimeException("상품 등록 중 오류가 발생했습니다.", e);
        }
    }

    // 상품 수정
    @Transactional
    public String editProduct(Long pno, ProductEditDTO productEditDTO) {
        log.info("editProduct pno" + pno);

        Optional<ProductEntity> productOptional = productRepository.findById(pno);
        if(productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();

            // 기본 정보 설정
            productEntity.setPrice(productEditDTO.getPrice());
            productEntity.setPqty(productEditDTO.getPqty());
            productEntity.setPfilename(productEditDTO.getPfilename());

            // 한국어 정보 설정
            productEntity.setPtitle_ko(productEditDTO.getPtitle_ko());
            productEntity.setPcontent_ko(productEditDTO.getPcontent_ko());
            productEntity.setPcategory_ko(productEditDTO.getPcategory_ko());

            // 영어 정보 설정
            productEntity.setPtitle_en(productEditDTO.getPtitle_en());
            productEntity.setPcontent_en(productEditDTO.getPcontent_en());
            productEntity.setPcategory_en(productEditDTO.getPcategory_en());

            // 일본어 정보 설정
            productEntity.setPtitle_ja(productEditDTO.getPtitle_ja());
            productEntity.setPcontent_ja(productEditDTO.getPcontent_ja());
            productEntity.setPcategory_ja(productEditDTO.getPcategory_ja());

            // 중국어 정보 설정
            productEntity.setPtitle_zh(productEditDTO.getPtitle_zh());
            productEntity.setPcontent_zh(productEditDTO.getPcontent_zh());
            productEntity.setPcategory_zh(productEditDTO.getPcategory_zh());

            productRepository.save(productEntity);

            // 알레르기 정보 업데이트
            productAllergyService.updateProductAllergies(productEntity, productEditDTO.getAllergySelectList());

            return "상품이 수정되었습니다.";
        } else {
            return "해당 제품을 찾을 수 없습니다.";
        }
    }

    //상품 논리 삭제
    @Transactional
    public String deleteProduct(Long pno) {
        log.info("deleteProduct pno: " + pno);

        Optional<ProductEntity> productOptional = productRepository.findById(pno);
        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();

            productEntity.setPdelete(true);

            productRepository.save(productEntity);

            return "상품이 삭제되었습니다.";
        } else {
            return "해당 제품을 찾을 수 없습니다.";
        }
    }
}