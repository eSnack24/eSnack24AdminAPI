package org.esnack24api.esnack24adminapi.customersupport.repository.search;

import com.querydsl.jpa.JPQLQuery;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.domain.FAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.domain.QNAEntity;
import org.esnack24api.esnack24adminapi.customersupport.domain.QQNAEntity;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class QNASearchImpl extends QuerydslRepositorySupport implements QNASearch {

    public QNASearchImpl() {
        super(FAQEntity.class);
    }

    @Override
    public PageResponseDTO<QNAListDTO> qnaList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                    pageRequestDTO.getSize(),
                    Sort.by("admno").ascending());

        QQNAEntity qna = QQNAEntity.qNAEntity;

        JPQLQuery<QNAEntity> query = from(qna);

        query.where(qna.qno.gt(0));
        query.where(qna.qdelete.isFalse());

        this.getQuerydsl().applyPagination(pageable, query);




        return null;
    }
}
