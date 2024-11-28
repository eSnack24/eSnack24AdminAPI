package org.esnack24api.esnack24adminapi.product.repository.search;

import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.common.page.PageRequest;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.product.dto.ProductAllergyListDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductDetailDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;

public interface ProductSearch {
    PageResponseDTO<ProductListDTO> listProductAll(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductAllergyListDTO> listProductAllergyInfo(PageRequestDTO pageRequestDTO);

    ProductDetailDTO getProductOne(Long pno);

}
