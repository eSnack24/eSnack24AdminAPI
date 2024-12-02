package org.esnack24api.esnack24adminapi.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductAllergyService productAllergyService;
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
            // 1. 파일 이름 생성 (UUID 사용)
            String originalFileName = productAddDTO.getFile().getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String savedFileName = UUID.randomUUID() + extension;

            // 2. 로컬 저장 (원본 + 썸네일)
            String savePath = "C:\\snack\\demo\\";

            // 원본 이미지 저장
            File originalFile = new File(savePath + savedFileName);
            productAddDTO.getFile().transferTo(originalFile);

            // 썸네일 이미지 저장
            File thumbnailFile = new File(savePath + "s_" + savedFileName);
            productAddDTO.getFile().transferTo(thumbnailFile);

            // 3. S3 업로드
            s3Uploader.upload(savePath + savedFileName);

            // 4. 제품 정보 저장
            ProductEntity productEntity = ProductEntity.builder()
                    .ptitle_ko(productAddDTO.getPtitle_ko())
                    .pcontent_ko(productAddDTO.getPcontent_ko())
                    .pcategory_ko(productAddDTO.getPcategory_ko())
                    .price(productAddDTO.getPrice())
                    .pqty(productAddDTO.getPqty())
                    .pfilename(savedFileName)
                    .build();

            ProductEntity savedProduct = productRepository.save(productEntity);
            productAllergyService.saveProductAllergies(savedProduct, productAddDTO.getAllergySelectList());

            return "상품이 등록되었습니다.";
        } catch (IOException e) {
            log.error("파일 업로드 실패", e);
            throw new RuntimeException("상품 등록 중 오류가 발생했습니다.");
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