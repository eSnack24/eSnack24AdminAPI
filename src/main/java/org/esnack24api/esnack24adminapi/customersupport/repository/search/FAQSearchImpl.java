package org.esnack24api.esnack24adminapi.customersupport.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.esnack24api.esnack24adminapi.customersupport.domain.FAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.domain.QFAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQDetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQListDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class FAQSearchImpl extends QuerydslRepositorySupport implements FAQSearch {

    public FAQSearchImpl() {
        super(FAQEntity.class);
    }

    @Override
    public List<FAQListDTO> listFAQ() {

        QFAQEntity faq = QFAQEntity.fAQEntity;
        JPQLQuery<FAQEntity> query = from(faq);

        query.where(faq.fdelete.eq(false));

        JPQLQuery<FAQListDTO> dtojpqlQuery = query.select(
                Projections.bean(FAQListDTO.class,
                        faq.fno,
                        faq.admin.admno,
                        faq.ftitle,
                        faq.fcategory,
                        faq.fdelete
                )
        );

        List<FAQListDTO> list = dtojpqlQuery.fetch();

        return list;
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
                        faq.admin.admno,
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
