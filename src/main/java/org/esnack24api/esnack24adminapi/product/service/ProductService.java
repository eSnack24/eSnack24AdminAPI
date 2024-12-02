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
        Optional<ProductEntity> productOptional = productRepository.findById(pno);

        if(productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();

            // 새로운 이미지 파일이 있는 경우에만 처리
            if(productEditDTO.getFile() != null && !productEditDTO.getFile().isEmpty()) {
                try {
                    // 1. 기존 파일 삭제
                    // 로컬 파일 삭제
                    String savePath = "C:\\snack\\demo\\";
                    File oldFile = new File(savePath + productEntity.getPfilename());
                    File oldThumbnail = new File(savePath + "s_" + productEntity.getPfilename());
                    if(oldFile.exists()) oldFile.delete();
                    if(oldThumbnail.exists()) oldThumbnail.delete();

                    // S3 파일 삭제
                    s3Uploader.removeS3File("product/" + productEntity.getPfilename());
                    s3Uploader.removeS3File("product/s_" + productEntity.getPfilename());

                    // 2. 새 파일명 생성
                    String originalFileName = productEditDTO.getFile().getOriginalFilename();
                    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String savedFileName = UUID.randomUUID() + extension;

                    // 3. 로컬 저장
                    File localFile = new File(savePath + savedFileName);
                    File thumbnailFile = new File(savePath + "s_" + savedFileName);
                    productEditDTO.getFile().transferTo(localFile);
                    productEditDTO.getFile().transferTo(thumbnailFile);

                    // 4. S3 업로드
                    s3Uploader.upload(savePath + savedFileName);

                    // 5. 파일명 업데이트
                    productEntity.setPfilename(savedFileName);
                } catch (IOException e) {
                    log.error("파일 업로드 실패", e);
                    throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.");
                }
            }

            // 나머지 정보 업데이트
            productEntity.setPtitle_ko(productEditDTO.getPtitle_ko());
            productEntity.setPtitle_en(productEditDTO.getPtitle_en());
            productEntity.setPtitle_ja(productEditDTO.getPtitle_ja());
            productEntity.setPtitle_zh(productEditDTO.getPtitle_zh());
            productEntity.setPcontent_ko(productEditDTO.getPcontent_ko());
            productEntity.setPcontent_en(productEditDTO.getPcontent_en());
            productEntity.setPcontent_ja(productEditDTO.getPcontent_ja());
            productEntity.setPcontent_zh(productEditDTO.getPcontent_zh());
            productEntity.setPcategory_ko(productEditDTO.getPcategory_ko());
            productEntity.setPrice(productEditDTO.getPrice());
            productEntity.setPqty(productEditDTO.getPqty());

            productRepository.save(productEntity);
            productAllergyService.updateProductAllergies(productEntity, productEditDTO.getAllergySelectList());

            return "상품이 수정되었습니다.";
        } else {
            return "해당 제품을 찾을 수 없습니다.";
        }
    }

    //상품데이터 논리 삭제(이미지는 물리 삭제)
    @Transactional
    public String deleteProduct(Long pno) {
        Optional<ProductEntity> productOptional = productRepository.findById(pno);

        if(productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();

            // 1. 로컬 파일 삭제
            String savePath = "C:\\snack\\demo\\";
            File originalFile = new File(savePath + productEntity.getPfilename());
            File thumbnailFile = new File(savePath + "s_" + productEntity.getPfilename());

            if(originalFile.exists()) originalFile.delete();
            if(thumbnailFile.exists()) thumbnailFile.delete();

            // 2. S3 파일 삭제
            s3Uploader.removeS3File("product/" + productEntity.getPfilename());
            s3Uploader.removeS3File("product/s_" + productEntity.getPfilename());

            // 3. DB에서 제품 정보 삭제
            productRepository.delete(productEntity);

            return "상품이 삭제되었습니다.";
        }

        return "해당 제품을 찾을 수 없습니다.";
    }
}