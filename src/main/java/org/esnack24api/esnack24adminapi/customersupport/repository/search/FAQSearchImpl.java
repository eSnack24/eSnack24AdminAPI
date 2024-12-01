package org.esnack24api.esnack24adminapi.customersupport.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.domain.FAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.domain.QFAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQDetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQListDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class FAQSearchImpl extends QuerydslRepositorySupport implements FAQSearch {

    public FAQSearchImpl() {
        super(FAQEntity.class);
    }

    @Override
    public PageResponseDTO<FAQListDTO> listFAQ(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("fno").ascending());

        QFAQEntity faq = QFAQEntity.fAQEntity;
        JPQLQuery<FAQEntity> query = from(faq);

        query.where(faq.fdelete.eq(false));

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<FAQListDTO> dtojpqlQuery = query.select(
                Projections.bean(FAQListDTO.class,
                        faq.fno,
                        faq.admin.admname,
                        faq.ftitle,
                        faq.fcategory,
                        faq.fdelete
                )
        );

        List<FAQListDTO> dtoList = dtojpqlQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<FAQListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public FAQDetailDTO detailFAQ(Long fno) {

        QFAQEntity faq = QFAQEntity.fAQEntity;
        JPQLQuery<FAQEntity> query = from(faq);

        query.where(faq.fdelete.eq(false));
        query.where(faq.fno.eq(fno));


        JPQLQuery<FAQDetailDTO> dtojpqlQuery = query.select(
                Projections.bean(FAQDetailDTO.class,
                        faq.fno,
                        faq.admin.admname,
                        faq.ftitle,
                        faq.fcategory,
                        faq.fdelete,
                        faq.fcontent
                )
        );

        FAQDetailDTO list = dtojpqlQuery.fetchOne();

        return list;
    }



}
