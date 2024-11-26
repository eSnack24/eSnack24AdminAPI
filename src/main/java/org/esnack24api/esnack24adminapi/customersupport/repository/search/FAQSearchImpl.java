package org.esnack24api.esnack24adminapi.customersupport.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.esnack24api.esnack24adminapi.customersupport.domain.FAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.domain.QFAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.dto.FAQListDTO;
import org.springframework.data.domain.PageImpl;
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
    public List<FAQListDTO> listFAQ() {

        QFAQEntity faq = QFAQEntity.fAQEntity;
        JPQLQuery<FAQEntity> query = from(faq);

        query.where(faq.fdelete.eq(false));

        JPQLQuery<FAQListDTO> dtojpqlQuery = query.select(
                Projections.bean(FAQListDTO.class,
                        faq.fno,
                        faq.admno,
                        faq.ftitle,
                        faq.fcategory,
                        faq.fdelete,
                        faq.fcontent
                )
        );

        List<FAQListDTO> list = dtojpqlQuery.fetch();

        return list;
    }


}
