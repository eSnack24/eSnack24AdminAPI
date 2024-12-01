package org.esnack24api.esnack24adminapi.community.repository.search.allergy;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.community.domain.QRequestAllergyEntity;
import org.esnack24api.esnack24adminapi.community.domain.RequestAllergyEntity;
import org.esnack24api.esnack24adminapi.community.dto.RequestAllergyListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class RequestAllergySearchImpl extends QuerydslRepositorySupport implements RequestAllergySearch {
    public RequestAllergySearchImpl() {
        super(RequestAllergyEntity.class);
    }

    @Override
    public PageResponseDTO<RequestAllergyListDTO> getAllergyList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        QRequestAllergyEntity requestAllergy = QRequestAllergyEntity.requestAllergyEntity;

        JPQLQuery<RequestAllergyEntity> query = from(requestAllergy);

        query.where(requestAllergy.cano.gt(0)
                .and(requestAllergy.cadelete.eq(false)));

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<RequestAllergyListDTO> tupleQuery = query.select(
                Projections.bean(RequestAllergyListDTO.class,
                        requestAllergy.cano,
                        requestAllergy.caallergy,
                        requestAllergy.catitle,
                        requestAllergy.caanswer,
                        requestAllergy.cadelete,
                        requestAllergy.caregdate,
                        requestAllergy.camoddate
                        )
        );

        List<RequestAllergyListDTO> allergyList = tupleQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<RequestAllergyListDTO>withAll()
                .dtoList(allergyList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

    }

    @Override
    public RequestAllergyListDTO getAllergyListById(Long cano) {

        QRequestAllergyEntity requestAllergy = QRequestAllergyEntity.requestAllergyEntity;

        JPQLQuery<RequestAllergyEntity> query = from(requestAllergy);

        query.where(requestAllergy.cano.eq(cano));

        RequestAllergyEntity allergy = query.fetchOne();

        if (allergy == null) {
            throw new IllegalArgumentException("해당 알러지를 찾을 수 없습니다.");
        }

        return RequestAllergyListDTO.builder()
                .cano(allergy.getCano())
                .caallergy(allergy.getCaallergy())
                .caanswer(allergy.getCaanswer())
                .catitle(allergy.getCatitle())
                .caregdate(allergy.getCaregdate())
                .camoddate(allergy.getCamoddate())
                .cadelete(allergy.isCadelete())
                .build();
    }
}
