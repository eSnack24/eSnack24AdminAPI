package org.esnack24api.esnack24adminapi.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.product.dto.*;
import org.esnack24api.esnack24adminapi.product.repository.ProductRepository;
import org.esnack24api.esnack24adminapi.upload.service.ImageUploadService;
import org.esnack24api.esnack24adminapi.upload.util.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductAllergyService productAllergyService;
    private final ImageUploadService imageUploadService;
    private final S3Uploader s3Uploader;

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
            String savedFileName = imageUploadService.uploadBase64File(productAddDTO.getPfilename());

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
        try {
            Optional<ProductEntity> productOptional = productRepository.findById(pno);
            if(productOptional.isPresent()) {
                ProductEntity productEntity = productOptional.get();

                // 이미지가 변경된 경우
                if (productEditDTO.getPfilename() != null && !productEditDTO.getPfilename().isEmpty()) {
                    // 기존 이미지 삭제
                    String oldFileName = productEntity.getPfilename();
                    deleteExistingFiles(oldFileName);

                    // 새 이미지 업로드
                    String savedFileName = imageUploadService.uploadBase64File(productEditDTO.getPfilename());
                    productEntity.setPfilename(savedFileName);
                }

                // 기본 정보 설정
                productEntity.setPrice(productEditDTO.getPrice());
                productEntity.setPqty(productEditDTO.getPqty());

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
        } catch (Exception e) {
            log.error("상품 수정 실패", e);
            throw new RuntimeException("상품 수정 중 오류가 발생했습니다.", e);
        }
    }

    private void deleteExistingFiles(String fileName) {
        try {
            // S3에서 파일 삭제
            s3Uploader.removeS3File("product/" + fileName);
            s3Uploader.removeS3File("product/s_" + fileName);

            // 로컬 파일 삭제
            String uploadPath = "C:\\snack\\demo\\";
            Path originalPath = Paths.get(uploadPath + fileName);
            Path thumbnailPath = Paths.get(uploadPath + "s_" + fileName);

            Files.deleteIfExists(originalPath);
            Files.deleteIfExists(thumbnailPath);
        } catch (IOException e) {
            log.error("파일 삭제 실패", e);
        }
    }

    //상품 데이터 논리 삭제(이미지는 물리 삭제)
    @Transactional
    public String deleteProduct(Long pno) {
        log.info("deleteProduct pno: " + pno);

        Optional<ProductEntity> productOptional = productRepository.findById(pno);
        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();

            try {
                // 기존 이미지 파일 삭제
                String fileName = productEntity.getPfilename();
                if (fileName != null && !fileName.isEmpty()) {
                    // S3에서 파일 삭제
                    s3Uploader.removeS3File("product/" + fileName);
                    s3Uploader.removeS3File("product/s_" + fileName);

                    // 로컬 파일 삭제
                    String uploadPath = "C:\\snack\\demo\\";
                    Path originalPath = Paths.get(uploadPath + fileName);
                    Path thumbnailPath = Paths.get(uploadPath + "s_" + fileName);

                    Files.deleteIfExists(originalPath);
                    Files.deleteIfExists(thumbnailPath);
                }

                // 논리적 삭제 처리
                productEntity.setPdelete(true);
                productRepository.save(productEntity);

                return "상품이 삭제되었습니다.";
            } catch (Exception e) {
                log.error("상품 삭제 실패", e);
                throw new RuntimeException("상품 삭제 중 오류가 발생했습니다.", e);
            }
        } else {
            return "해당 제품을 찾을 수 없습니다.";
        }
    }
}