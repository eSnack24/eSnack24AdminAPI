package org.esnack24api.esnack24adminapi.admin.repository.search;

import org.esnack24api.esnack24adminapi.admin.dto.AdminListDTO;
import org.esnack24api.esnack24adminapi.admin.dto.AdminReadDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminSearch {

    PageResponseDTO<AdminListDTO> adminList(PageRequestDTO pageRequestDTO);

    PageResponseDTO<AdminListDTO> adminListByRole(String role, PageRequestDTO pageRequestDTO);

    @Query("""
        select
           new org.esnack24api.esnack24adminapi.admin.dto.AdminReadDTO(
           a.admid, a.admname, a.admrole, a.admregdate, a.admmoddate
           )
        from
            AdminEntity a
        where
            a.admno = :admno
    """)
    AdminReadDTO getAdmin(@Param("admno") Long admno);
}
