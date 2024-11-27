package org.esnack24api.esnack24adminapi.admin.repository;

import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.esnack24api.esnack24adminapi.admin.dto.AdminReadDTO;
import org.esnack24api.esnack24adminapi.admin.repository.search.AdminSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long>, AdminSearch {

    Optional<AdminEntity> findByAdmid(String admid);

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
