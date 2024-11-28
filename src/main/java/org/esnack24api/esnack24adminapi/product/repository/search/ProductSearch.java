package org.esnack24api.esnack24adminapi.product.repository.search;

import org.esnack24api.esnack24adminapi.common.page.PageRequest;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.product.dto.ProductAddDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductAllergyListDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductDetailDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;

public interface ProductSearch {
    PageResponse<ProductListDTO> listProductAll(PageRequest pageRequest);

    PageResponse<ProductAllergyListDTO> listProductAllergyInfo(PageRequest pageRequest);

    ProductDetailDTO getProductOne(Long pno);

}
