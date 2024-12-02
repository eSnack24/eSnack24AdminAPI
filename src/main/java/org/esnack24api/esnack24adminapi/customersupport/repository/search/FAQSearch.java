package org.esnack24api.esnack24adminapi.customersupport.repository.search;

import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQDetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQListDTO;

import java.util.List;

public interface FAQSearch {

    PageResponseDTO<FAQListDTO> listFAQ(PageRequestDTO pageRequestDTO);

    FAQDetailDTO detailFAQ(Long fno);
}
