package org.esnack24api.esnack24adminapi.admin.repository.search;

import org.esnack24api.esnack24adminapi.admin.dto.AdminListDTO;
import org.esnack24api.esnack24adminapi.admin.dto.AdminWorkListDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;

public interface AdminSearch {

    PageResponseDTO<AdminListDTO> adminList(PageRequestDTO pageRequestDTO);

    PageResponseDTO<AdminListDTO> adminListByRole(String role, PageRequestDTO pageRequestDTO);

    PageResponseDTO<AdminWorkListDTO> adminWorkList(PageRequestDTO pageRequestDTO);
}
