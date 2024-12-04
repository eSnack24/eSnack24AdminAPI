package org.esnack24api.esnack24adminapi.community.repository.search.product;

import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.community.dto.RequestAllergyListDTO;
import org.esnack24api.esnack24adminapi.community.dto.RequestProductListDTO;


public interface RequestProductSearch  {

    PageResponseDTO<RequestProductListDTO> getRProductList(PageRequestDTO pageRequestDTO);

    RequestProductListDTO getProductById(Long cpno);

    PageResponseDTO<RequestProductListDTO> getTFProductList(Boolean status, PageRequestDTO pageRequestDTO);

}
