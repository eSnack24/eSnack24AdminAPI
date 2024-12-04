package org.esnack24api.esnack24adminapi.community.repository.search.allergy;


import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.community.dto.RequestAllergyListDTO;

public interface RequestAllergySearch {

    PageResponseDTO<RequestAllergyListDTO> getAllergyList(PageRequestDTO pageRequestDTO);

    RequestAllergyListDTO getAllergyListById(Long cano);

    PageResponseDTO<RequestAllergyListDTO> getTFAllergyList(Boolean status, PageRequestDTO pageRequestDTO);



}
