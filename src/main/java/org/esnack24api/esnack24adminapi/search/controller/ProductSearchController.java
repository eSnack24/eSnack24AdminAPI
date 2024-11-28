package org.esnack24api.esnack24adminapi.search.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductAllergyListDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;
import org.esnack24api.esnack24adminapi.product.service.ProductService;
import org.esnack24api.esnack24adminapi.search.dto.ProductAllergySearchDTO;
import org.esnack24api.esnack24adminapi.search.dto.ProductSearchDTO;
import org.esnack24api.esnack24adminapi.search.service.ProductSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/api/v1/product")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    // 제품 정보 검색
    @GetMapping("/search")
    // @RequestParam 대신 @ModelAttribute 사용 : 코드가 간결해짐
    public ResponseEntity<PageResponseDTO<ProductListDTO>> searchProducts(@ModelAttribute ProductSearchDTO productSearchDTO) {
        log.info("searchProducts");

        PageResponseDTO<ProductListDTO> responseDTO = productSearchService.searchProducts(productSearchDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 알레르기 정보 포함 검색
    @GetMapping("/search--allergy")
    public ResponseEntity<PageResponseDTO<ProductAllergyListDTO>> searchProductsWithAllergy(@ModelAttribute ProductAllergySearchDTO productAllergySearchDTO) {
        log.info("search Allergy Products");

        PageResponseDTO<ProductAllergyListDTO> responseDTO = productSearchService.searchProductsWithAllergy(productAllergySearchDTO);
        return ResponseEntity.ok(responseDTO);
    }
}