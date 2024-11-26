package org.esnack24api.esnack24adminapi.customersupport.repository.search;

import org.esnack24api.esnack24adminapi.customersupport.domain.FAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.dto.FAQListDTO;

import java.util.List;

public interface FAQSearch {

    List<FAQListDTO> listFAQ();
}
