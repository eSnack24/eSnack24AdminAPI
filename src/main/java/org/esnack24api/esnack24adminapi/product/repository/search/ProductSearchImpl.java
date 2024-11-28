package org.esnack24api.esnack24adminapi.product.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.allergy.domain.AllergyEntity;
import org.esnack24api.esnack24adminapi.allergy.domain.QAllergyEntity;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.common.page.PageRequest;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.product.domain.QProductAllergyEntity;
import org.esnack24api.esnack24adminapi.product.domain.QProductEntity;
import org.esnack24api.esnack24adminapi.product.dto.ProductAddDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductAllergyListDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductDetailDTO;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;
import org.esnack24api.esnack24adminapi.search.dto.ProductAllergySearchDTO;
import org.esnack24api.esnack24adminapi.search.dto.ProductSearchDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(ProductEntity.class);
    }

    // 상품 리스트를 조회
    @Override
    public PageResponseDTO<ProductListDTO> listProductAll(PageRequestDTO pageRequestDTO) {
        QProductEntity product = QProductEntity.productEntity;

        JPQLQuery<ProductEntity> query = from(product);

        query.where(product.pdelete.eq(false));

        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize();

        query.offset((long) (page - 1) * size)
                .limit(size);

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
        long totalCount = query.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(list)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    // 상품-알러지 리스트 조회
    @Override
    public PageResponseDTO<ProductAllergyListDTO> listProductAllergyInfo(PageRequestDTO pageRequestDTO) {
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
                .where(product.pdelete.eq(false))
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

        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize();

        query.offset((long) (page - 1) * size)
                .limit(size);

        List<ProductAllergyListDTO> list = query.fetch();
        long totalCount = query.fetchCount();

        return PageResponseDTO.<ProductAllergyListDTO>withAll()
                .dtoList(list)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    // 제품 상세 조회
    @Override
    public ProductDetailDTO getProductOne(Long pno) {
        QProductEntity product = QProductEntity.productEntity;
        QProductAllergyEntity productAllergy = QProductAllergyEntity.productAllergyEntity;
        QAllergyEntity allergy = QAllergyEntity.allergyEntity;

        StringTemplate allergyInfo = Expressions.stringTemplate(
                "group_concat({0})",
                allergy.atitle_ko
        );

        return from(product)
                .leftJoin(productAllergy).on(product.pno.eq(productAllergy.product.pno))
                .leftJoin(allergy).on(productAllergy.allergy.ano.eq(allergy.ano))
                .where(product.pno.eq(pno))
                .where(product.pdelete.eq(false))
                .groupBy(product.pno)
                .select(Projections.constructor(ProductDetailDTO.class,
                        product.pno,
                        product.price,
                        product.pqty,
                        product.pdelete,
                        product.pfilename,
                        product.pregdate,
                        product.pmoddate,
                        product.ptitle_ko,
                        product.pcontent_ko,
                        product.pcategory_ko,
                        product.ptitle_en,
                        product.ptitle_ja,
                        product.ptitle_zh,
                        product.pcontent_en,
                        product.pcontent_ja,
                        product.pcontent_zh,
                        product.pcategory_en,
                        product.pcategory_ja,
                        product.pcategory_zh,
                        allergyInfo
                ))
                .fetchOne();
    }

        // 제품 정보 검색 구현
        @Override
        public PageResponseDTO<ProductListDTO> searchProducts(ProductSearchDTO productSearchDTO) {
            QProductEntity product = QProductEntity.productEntity;

            JPQLQuery<ProductEntity> query = from(product);

            // 논리 삭제된 제품 제외
            query.where(product.pdelete.eq(false));

            // 제품명 검색
            if (productSearchDTO.getPtitle_ko() != null && !productSearchDTO.getPtitle_ko().trim().isEmpty()) {
                query.where(product.ptitle_ko.containsIgnoreCase(productSearchDTO.getPtitle_ko()));
            }

            // 카테고리 검색
            if (productSearchDTO.getPcategory_ko() != null && !productSearchDTO.getPcategory_ko().trim().isEmpty()) {
                query.where(product.pcategory_ko.eq(productSearchDTO.getPcategory_ko()));
            }

            // 페이징 처리
            int page = productSearchDTO.getPage();
            int size = productSearchDTO.getSize();
            query.offset((long) (page - 1) * size).limit(size);

            // DTO 매핑
            JPQLQuery<ProductListDTO> dtoQuery = query.select(
                    Projections.bean(ProductListDTO.class,
                            product.pno,
                            product.ptitle_ko,
                            product.pcategory_ko,
                            product.price,
                            product.pqty,
                            product.pfilename
                    )
            );

            List<ProductListDTO> list = dtoQuery.fetch();
            long totalCount = query.fetchCount();

            return PageResponseDTO.<ProductListDTO>withAll()
                    .dtoList(list)
                    .pageRequestDTO(productSearchDTO)
                    .totalCount(totalCount)
                    .build();
        }

        // 알레르기 정보 포함 검색 구현
        @Override
        public PageResponseDTO<ProductAllergyListDTO> searchProductsWithAllergy(ProductAllergySearchDTO productAllergySearchDTO) {
            QProductEntity product = QProductEntity.productEntity;
            QProductAllergyEntity productAllergy = QProductAllergyEntity.productAllergyEntity;
            QAllergyEntity allergy = QAllergyEntity.allergyEntity;

            List<Long> allergyIds = productAllergySearchDTO.getAllergySelectList();

            int page = productAllergySearchDTO.getPage();
            int size = productAllergySearchDTO.getSize();

            JPQLQuery<ProductEntity> query;

            if (allergyIds != null && !allergyIds.isEmpty()) {
                // 알레르기 ID가 있는 경우

                // 서브쿼리로 검색 조건에 맞는 제품의 pno를 가져옵니다.
                JPQLQuery<Long> subQuery = JPAExpressions.select(productAllergy.product.pno)
                        .from(productAllergy)
                        .where(productAllergy.allergy.ano.in(allergyIds))
                        .groupBy(productAllergy.product.pno)
                        .having(productAllergy.allergy.ano.countDistinct().eq((long) allergyIds.size()));

                // 메인 쿼리에서 해당 제품의 모든 알레르기 정보를 가져옵니다.
                query = from(product)
                        .leftJoin(productAllergy).on(product.pno.eq(productAllergy.product.pno))
                        .leftJoin(allergy).on(productAllergy.allergy.ano.eq(allergy.ano))
                        .where(product.pno.in(subQuery))
                        .where(product.pdelete.eq(false))
                        .groupBy(product.pno);
            } else {
                // 알레르기 ID가 없으면 알레르기 정보가 없는 제품을 조회합니다.

                // 모든 제품과 알레르기 정보를 left join 합니다.
                query = from(product)
                        .leftJoin(productAllergy).on(product.pno.eq(productAllergy.product.pno))
                        .leftJoin(allergy).on(productAllergy.allergy.ano.eq(allergy.ano))
                        .where(product.pdelete.eq(false))
                        .groupBy(product.pno)
                        .having(productAllergy.allergy.ano.count().eq(0L)); // 알레르기 정보가 없는 제품만 선택
            }

            // DTO 매핑
            JPQLQuery<ProductAllergyListDTO> dtoQuery = query.select(Projections.constructor(ProductAllergyListDTO.class,
                    product.pno,
                    product.ptitle_ko,
                    product.pcategory_ko,
                    product.pqty,
                    product.price,
                    product.pfilename,
                    product.pregdate,
                    product.pmoddate,
                    Expressions.stringTemplate("COALESCE(group_concat({0}), '알레르기 유발성분 없음')", allergy.atitle_ko).as("allergyInfo")
            ));

            // 페이징 처리
            dtoQuery.offset((long) (page - 1) * size).limit(size);

            List<ProductAllergyListDTO> list = dtoQuery.fetch();

            // 총 개수 구하기
            long totalCount = dtoQuery.fetchCount();

            return PageResponseDTO.<ProductAllergyListDTO>withAll()
                    .dtoList(list)
                    .pageRequestDTO(productAllergySearchDTO)
                    .totalCount(totalCount)
                    .build();
        }





}
