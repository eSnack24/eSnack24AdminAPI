package org.esnack24api.esnack24adminapi.exchangee_rate.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.domain.QAdminEntity;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.exchangee_rate.domain.ExchangeRateEntity;
import org.esnack24api.esnack24adminapi.exchangee_rate.domain.QExchangeRateEntity;
import org.esnack24api.esnack24adminapi.exchangee_rate.dto.RateDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class ExchangeRateSearchImpl extends QuerydslRepositorySupport implements ExchangeRateSearch{

    public ExchangeRateSearchImpl(){
        super(ExchangeRateEntity.class);
    }


    @Override
    public PageResponseDTO<RateDTO> getRateList(PageRequestDTO pageRequestDTO) {

        Pageable pageable =
                PageRequest.of(pageRequestDTO.getPage() - 1,
                        pageRequestDTO.getSize());

        QExchangeRateEntity exchangeRate = QExchangeRateEntity.exchangeRateEntity;
        QAdminEntity admin = QAdminEntity.adminEntity;

        JPQLQuery<ExchangeRateEntity> query = from(exchangeRate);
        query.leftJoin(admin).on(exchangeRate.admin.eq(admin));

        query.where(exchangeRate.erno.gt(0));
        query.where(exchangeRate.erdelete.isFalse());

        query.orderBy(exchangeRate.erupdate.desc());

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<RateDTO> tupleQuery = query.select(
                Projections.bean(RateDTO.class,
                        exchangeRate.erno,
                        exchangeRate.baseCurrency,
                        exchangeRate.targetCurrency,
                        exchangeRate.rate,
                        exchangeRate.erdelete,
                        exchangeRate.erupdate,
                        admin.admname
                )
        );

        List<RateDTO> dtoList = tupleQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<RateDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public RateDTO getRate(Long erno) {

        QExchangeRateEntity exchangeRate = QExchangeRateEntity.exchangeRateEntity;
        QAdminEntity admin = QAdminEntity.adminEntity;

        JPQLQuery<ExchangeRateEntity> query = from(exchangeRate);
        query.leftJoin(admin).on(exchangeRate.admin.eq(admin));;

        query.where(exchangeRate.erno.eq(erno));

        JPQLQuery<RateDTO> tupleQuery = query.select(
                Projections.bean(RateDTO.class,
                        exchangeRate.erno,
                        exchangeRate.baseCurrency,
                        exchangeRate.targetCurrency,
                        exchangeRate.rate,
                        exchangeRate.erdelete,
                        exchangeRate.erupdate,
                        admin.admname
                )
        );

        return tupleQuery.fetchOne();
    }
}
