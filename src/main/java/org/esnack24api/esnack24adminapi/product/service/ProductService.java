package org.esnack24api.esnack24adminapi.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.page.PageRequest;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.product.dto.ProductAllergyListDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;
import org.esnack24api.esnack24adminapi.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 리스트 조회
    public PageResponse<ProductListDTO> getProductList(PageRequest pageRequest) {
        log.info("getProductList");
        return productRepository.listProductAll(pageRequest);
    }

    // 상품-알러지 리스트 조회
    @Transactional(readOnly = true)
    public PageResponse<ProductAllergyListDTO> getProductAllergyList(PageRequest pageRequest) {
        return productRepository.listProductAllergyInfo(pageRequest);
    }

}
