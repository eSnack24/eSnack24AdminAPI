package org.esnack24api.esnack24adminapi.customersupport.repository.search;

import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQDetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQListDTO;

import java.util.List;

public interface FAQSearch {

    List<FAQListDTO> listFAQ();

    FAQDetailDTO detailFAQ(Long fno);
}
