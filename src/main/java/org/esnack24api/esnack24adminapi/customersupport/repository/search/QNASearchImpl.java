package org.esnack24api.esnack24adminapi.customersupport.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.esnack24api.esnack24adminapi.admin.domain.QAdminEntity;
import org.esnack24api.esnack24adminapi.admin.dto.AdminWorkListDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.domain.FAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.domain.QNAEntity;
import org.esnack24api.esnack24adminapi.customersupport.domain.QQNAEntity;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;
import org.esnack24api.esnack24adminapi.product.domain.QProductEntity;
import org.esnack24api.esnack24adminapi.user.domain.QUserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.sql.Timestamp;
import java.util.List;

public class QNASearchImpl extends QuerydslRepositorySupport implements QNASearch {

    public QNASearchImpl() {
        super(QNAEntity.class);
    }

    @Override
    public PageResponseDTO<QNAListDTO> qnaList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                    pageRequestDTO.getSize(),
                Sort.by("admin.admname").ascending());

        QQNAEntity qna = QQNAEntity.qNAEntity;
        QUserEntity user = QUserEntity.userEntity;
        QProductEntity product = QProductEntity.productEntity;
        QAdminEntity admin = QAdminEntity.adminEntity;

        JPQLQuery<QNAEntity> query = from(qna);

        query.leftJoin(user).on(user.eq(qna.user));
        query.leftJoin(product).on(product.eq(qna.product));
        query.leftJoin(admin).on(admin.eq(qna.admin));

        query.where(qna.qno.gt(0));
        query.where(qna.qdelete.isFalse());

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<QNAListDTO> tupleQuery = query.select(
                Projections.bean(QNAListDTO.class,
                        qna.qno,
                        qna.user.uemail,
                        qna.product.ptitle_ko,
                        qna.admin.admname,
                        qna.qtitle,
                        qna.qdelete,
                        qna.qregdate,
                        qna.qmoddate,
                        qna.qstatus
                )
        );

        List<QNAListDTO> dtoList = tupleQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<QNAListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

    }
}
