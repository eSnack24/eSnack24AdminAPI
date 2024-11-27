package org.esnack24api.esnack24adminapi.admin.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.esnack24api.esnack24adminapi.admin.domain.QAdminEntity;
import org.esnack24api.esnack24adminapi.admin.dto.AdminListDTO;
import org.esnack24api.esnack24adminapi.admin.dto.AdminWorkListDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.domain.QQNAEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class AdminSearchImpl extends QuerydslRepositorySupport implements AdminSearch {

    public AdminSearchImpl() {
        super(AdminEntity.class);
    }

    private PageResponseDTO<AdminListDTO> returnRoleListDTO(
            QAdminEntity admin, JPQLQuery<AdminEntity> query, PageRequestDTO pageRequestDTO) {

        JPQLQuery<AdminListDTO> tupleQuery = query.select(
                Projections.bean(AdminListDTO.class,
                        admin.admno,
                        admin.admid,
                        admin.admrole,
                        admin.admname,
                        admin.admregdate,
                        admin.admmoddate
                )
        );

        List<AdminListDTO> dtoList = tupleQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<AdminListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
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

        return returnRoleListDTO(admin, query, pageRequestDTO);
    }

    @Override
    public PageResponseDTO<AdminListDTO> adminListByRole(String role, PageRequestDTO pageRequestDTO) {

        Pageable pageable =
                PageRequest.of(pageRequestDTO.getPage() - 1,
                        pageRequestDTO.getSize(),
                        Sort.by("admno").ascending());

        QAdminEntity admin = QAdminEntity.adminEntity;

        JPQLQuery<AdminEntity> query = from(admin);

        query.where(admin.admno.gt(0));
        query.where(admin.admrole.eq(role));
        query.where(admin.admdelete.isFalse());

        this.getQuerydsl().applyPagination(pageable, query);

        return returnRoleListDTO(admin, query, pageRequestDTO);
    }

    @Override
    public PageResponseDTO<AdminWorkListDTO> adminWorkList(String order_by, PageRequestDTO pageRequestDTO) {

        Pageable pageable =
                PageRequest.of(pageRequestDTO.getPage() - 1,
                        pageRequestDTO.getSize());

        QAdminEntity admin = QAdminEntity.adminEntity;
        QQNAEntity qna = QQNAEntity.qNAEntity;

        JPQLQuery<AdminEntity> query = from(admin);
        query.leftJoin(qna).on(qna.admin.eq(admin));

        query.where(admin.admno.gt(0));
        query.where(admin.admdelete.isFalse());

        query.groupBy(admin);

        if (order_by.equals("asc")) {
            query.orderBy(qna.count().asc());
        } else {
            query.orderBy(qna.count().desc());
        }

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<AdminWorkListDTO> tupleQuery = query.select(
                Projections.bean(AdminWorkListDTO.class,
                        admin.admno,
                        admin.admid,
                        admin.admrole,
                        admin.admname,
                        qna.count().as("count")
                )
        );

        List<AdminWorkListDTO> dtoList = tupleQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<AdminWorkListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
