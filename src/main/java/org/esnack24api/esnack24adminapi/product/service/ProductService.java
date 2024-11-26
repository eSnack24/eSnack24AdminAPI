package org.esnack24api.esnack24adminapi.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.product.dto.ProductDetailDTO;
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
    public PageResponseDTO<ProductListDTO> getList(PageRequestDTO pageRequestDTO) {
        log.info("getProductList");
        return productRepository.listProductAll(pageRequestDTO);
    }

    // 상품 상세 조회
}