package org.esnack24api.esnack24adminapi.community.repository.search.product;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.community.domain.QRequestProductEntity;
import org.esnack24api.esnack24adminapi.community.domain.RequestProductEntity;
import org.esnack24api.esnack24adminapi.community.dto.RequestProductListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class RequestProductSearchImpl extends QuerydslRepositorySupport implements RequestProductSearch {

    public RequestProductSearchImpl() {
        super(RequestProductEntity.class);
    }

    @Override
    public PageResponseDTO<RequestProductListDTO> getRProductList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        QRequestProductEntity requestProduct = QRequestProductEntity.requestProductEntity;

        JPQLQuery<RequestProductEntity> query = from(requestProduct);

        query.where(requestProduct.cpno.gt(0)
                .and(requestProduct.cpdelete.eq(false)));

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<RequestProductListDTO> tupleQuery = query.select(
                Projections.bean(RequestProductListDTO.class,
                        requestProduct.cpno,
                        requestProduct.cpproduct,
                        requestProduct.cptitle,
                        requestProduct.cpanswer,
                        requestProduct.cpregdate,
                        requestProduct.cpmoddate,
                        requestProduct.cpdelete
                )
        );

        List<RequestProductListDTO> dtoList = tupleQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<RequestProductListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public RequestProductListDTO getProductById(Long cpno) {
        QRequestProductEntity requestProduct = QRequestProductEntity.requestProductEntity;

        JPQLQuery<RequestProductEntity> query = from(requestProduct);

        query.where(requestProduct.cpno.eq(cpno));

        RequestProductEntity product = query.fetchOne();

        if (product == null) {
            throw new IllegalArgumentException("해당 상품을 찾을 수 없습니다.");
        }

        return RequestProductListDTO.builder()
                .cpno(product.getCpno())
                .cpproduct(product.getCpproduct())
                .cptitle(product.getCptitle())
                .cpanswer(product.getCpanswer())
                .cpregdate(product.getCpregdate())
                .cpmoddate(product.getCpmoddate())
                .cpdelete(product.isCpdelete())
                .build();
    }



}
