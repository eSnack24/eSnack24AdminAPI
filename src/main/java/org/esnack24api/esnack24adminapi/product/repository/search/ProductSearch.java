package org.esnack24api.esnack24adminapi.product.repository.search;

import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.common.page.PageRequest;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.product.dto.ProductAllergyListDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductDetailDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;
import org.esnack24api.esnack24adminapi.search.dto.ProductAllergySearchDTO;
import org.esnack24api.esnack24adminapi.search.dto.ProductSearchDTO;

public interface ProductSearch {

    PageResponseDTO<ProductListDTO> listProductAll(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductAllergyListDTO> listProductAllergyInfo(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductListDTO> searchProducts(ProductSearchDTO productSearchDTO);

    PageResponseDTO<ProductAllergyListDTO> searchProductsWithAllergy(ProductAllergySearchDTO productAllergySearchDTO);

    ProductDetailDTO getProductOne(Long pno);

}
