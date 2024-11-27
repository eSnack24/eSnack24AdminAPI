package org.esnack24api.esnack24adminapi.customersupport.repository.search;

import org.esnack24api.esnack24adminapi.admin.dto.AdminListDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNADetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;

public interface QNASearch {

    PageResponseDTO<QNAListDTO> qnaList(PageRequestDTO pageRequestDTO);

    QNADetailDTO qnaDetail(Long pno);
}
