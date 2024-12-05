package org.esnack24api.esnack24adminapi.exchangee_rate.repository.search;

import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.exchangee_rate.dto.RateDTO;

public interface ExchangeRateSearch {

    PageResponseDTO<RateDTO> getRateList(PageRequestDTO requestDTO);

    RateDTO getRate(Long erno);
}
