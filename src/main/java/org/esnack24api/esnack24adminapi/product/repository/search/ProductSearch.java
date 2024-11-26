package org.esnack24api.esnack24adminapi.product.repository.search;

import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearch {
    Page<ProductEntity> listProducts(Pageable pageable);
    PageResponseDTO<ProductListDTO> listProductAll(PageRequestDTO pageRequestDTO);
}
