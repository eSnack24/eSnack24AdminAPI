package org.esnack24api.esnack24adminapi.review.repository.search;

import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;
import org.esnack24api.esnack24adminapi.review.dto.ReviewListDTO;

public interface ReviewSearch {

    PageResponseDTO<ReviewListDTO> reviewList(PageRequestDTO pageRequestDTO);
}
