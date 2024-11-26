package org.esnack24api.esnack24adminapi.admin.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.esnack24api.esnack24adminapi.admin.domain.QAdminEntity;
import org.esnack24api.esnack24adminapi.admin.dto.AdminListDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class AdminSearchImpl extends QuerydslRepositorySupport implements AdminSearch {

    public AdminSearchImpl() {
        super(AdminEntity.class);
    }


    @Override
    public PageResponseDTO<AdminListDTO> adminList(PageRequestDTO pageRequestDTO) {

        Pageable pageable =
                PageRequest.of(pageRequestDTO.getPage() - 1,
                        pageRequestDTO.getSize(),
                        Sort.by("admno").ascending());

        QAdminEntity admin = QAdminEntity.adminEntity;

        JPQLQuery<AdminEntity> query = from(admin);

        query.where(admin.admno.gt(0));
        query.where(admin.admdelete.isFalse());

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<Tuple> tupleQuery =
                query.select(
                        admin.admno,
                        admin.admid,
                        admin.admpw,
                        admin.admregdate,
                        admin.admmoddate,
                        admin.admrole
                );

        List<Tuple> tupleList = tupleQuery.fetch();

        log.info(tupleList);

        if(tupleList.isEmpty()) {
            return null;
        }

        List<AdminListDTO> dtoList = new ArrayList<>();

        tupleList.forEach(t -> {

            Long admno = t.get(admin.admno);
            String admid = t.get(admin.admid);
            String admpw = t.get(admin.admpw);
            Timestamp admregdate = t.get(admin.admregdate);
            Timestamp admmoddate = t.get(admin.admmoddate);
            String admrole = t.get(admin.admrole);


            AdminListDTO dto = new AdminListDTO();
            dto.setAdmno(admno);
            dto.setAdmid(admid);
            dto.setAdmpw(admpw);
            dto.setAdmrole(admrole);
            dto.setAdmregdate(admregdate);
            dto.setAdmmoddate(admmoddate);

            dtoList.add(dto);
        });

        long total = query.fetchCount();

        return PageResponseDTO.<AdminListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
