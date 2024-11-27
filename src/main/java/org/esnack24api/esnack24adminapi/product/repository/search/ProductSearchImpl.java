package org.esnack24api.esnack24adminapi.product.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.allergy.domain.QAllergyEntity;
import org.esnack24api.esnack24adminapi.common.page.PageRequest;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.product.domain.QProductAllergyEntity;
import org.esnack24api.esnack24adminapi.product.domain.QProductEntity;
import org.esnack24api.esnack24adminapi.product.dto.ProductAllergyListDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(ProductEntity.class);
    }

    // 상품 리스트를 조회
    @Override
    public PageResponse<ProductListDTO> listProductAll(PageRequest pageRequest) {
        QProductEntity product = QProductEntity.productEntity;

        JPQLQuery<ProductEntity> query = from(product);
        query.where(product.pno.gt(0));

        query.offset((long) (pageRequest.getPage() - 1) * pageRequest.getSize())
                .limit(pageRequest.getSize());

        JPQLQuery<ProductListDTO> dtoQuery = query.select(
                Projections.bean(ProductListDTO.class,
                        product.pno,
                        product.pqty,
                        product.pfilename,
                        product.pregdate,
                        product.pmoddate,
                        product.price,
                        product.pdelete,
                        product.ptitle_ko,
                        product.pcontent_ko,
                        product.pcategory_ko
                )
        );

        List<ProductListDTO> list = dtoQuery.fetch();
        long total = query.fetchCount();

        return PageResponse.<ProductListDTO>with()
                .list(list)
                .total((int) total)
                .pageRequest(pageRequest)
                .build();
    }

    // 상품-알러지 리스트 조회
    @Override
    public PageResponse<ProductAllergyListDTO> listProductAllergyInfo(PageRequest pageRequest) {
        QProductEntity product = QProductEntity.productEntity;
        QProductAllergyEntity productAllergy = QProductAllergyEntity.productAllergyEntity;
        QAllergyEntity allergy = QAllergyEntity.allergyEntity;

        StringTemplate allergyInfo = Expressions.stringTemplate(
                "group_concat({0})",
                allergy.atitle_ko
        );

        JPQLQuery<ProductAllergyListDTO> query = from(product)
                .leftJoin(productAllergy).on(product.pno.eq(productAllergy.product.pno))
                .leftJoin(allergy).on(productAllergy.allergy.ano.eq(allergy.ano))
                .groupBy(product.pno)
                .select(Projections.constructor(ProductAllergyListDTO.class,
                        product.pno,
                        product.ptitle_ko,
                        product.pcategory_ko,
                        product.pqty,
                        product.price,
                        product.pfilename,
                        product.pregdate,
                        product.pmoddate,
                        allergyInfo
                ));

        query.offset((long) (pageRequest.getPage() - 1) * pageRequest.getSize())
                .limit(pageRequest.getSize());

        List<ProductAllergyListDTO> list = query.fetch();
        long total = query.fetchCount();

        return PageResponse.<ProductAllergyListDTO>with()
                .list(list)
                .total((int) total)
                .pageRequest(pageRequest)
                .build();
    }


}