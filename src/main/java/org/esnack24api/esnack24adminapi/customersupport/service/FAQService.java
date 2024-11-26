package org.esnack24api.esnack24adminapi.customersupport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.customersupport.dto.FAQDetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.FAQListDTO;
import org.esnack24api.esnack24adminapi.customersupport.repository.FAQRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class FAQService {

    private final FAQRepository faqRepository;

    public List<FAQListDTO> getFAQs() {
        return faqRepository.listFAQ();
    }

    public FAQDetailDTO getDetailFAQs(Long fno){
        return faqRepository.detailFAQ(fno);
    }
}
