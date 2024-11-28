package org.esnack24api.esnack24adminapi.search.service;

import lombok.RequiredArgsConstructor;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductAllergyListDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;
import org.esnack24api.esnack24adminapi.product.repository.ProductRepository;
import org.esnack24api.esnack24adminapi.search.dto.ProductAllergySearchDTO;
import org.esnack24api.esnack24adminapi.search.dto.ProductSearchDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRepository productRepository;

    // 제품 정보 검색
    @Transactional(readOnly = true)
    public PageResponseDTO<ProductListDTO> searchProducts(ProductSearchDTO productSearchDTO) {
        return productRepository.searchProducts(productSearchDTO);
    }

    // 알레르기 정보 포함 검색
    @Transactional(readOnly = true)
    public PageResponseDTO<ProductAllergyListDTO> searchProductsWithAllergy(ProductAllergySearchDTO productAllergySearchDTO) {
        return productRepository.searchProductsWithAllergy(productAllergySearchDTO);
    }
}