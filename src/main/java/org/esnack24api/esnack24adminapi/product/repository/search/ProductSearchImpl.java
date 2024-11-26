package org.esnack24api.esnack24adminapi.product.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.page.PageRequest;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.product.domain.QProductEntity;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(ProductEntity.class);
    }

    // 페이지네이션된 전체 상품 리스트를 조회하는 메서드
    @Override
    public PageResponse<ProductListDTO> listProductAll(PageRequest pageRequest) {

        QProductEntity product = QProductEntity.productEntity;

        // 기본 JPQL 쿼리를 작성. 상품 데이터베이스 테이블로부터 데이터를 조회
        JPQLQuery<ProductEntity> query = from(product);
        query.where(product.pno.gt(0));

        // 페이지네이션 직접 적용
        // 페이지 시작 위치(offset)과 페이지 크기(limit)를 설정하여 필요한 데이터만 가져옴
        query.offset((long) (pageRequest.getPage() - 1) * pageRequest.getSize())
                // 한 페이지에 표시할 최대 개수를 설정
                .limit(pageRequest.getSize());

        // JPQL 쿼리를 ProductListDTO로 변환하여 조회
        // Projections.bean() 메서드를 사용해 엔티티 필드를 DTO로 매핑
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

        // 결과 목록을 fetch하고 전체 개수도 fetch
        List<ProductListDTO> list = dtoQuery.fetch();
        long total = query.fetchCount();

        // PageResponse 빌드하여 반환
        return PageResponse.<ProductListDTO>with()
                .list(list)
                .total((int) total)
                .pageRequest(pageRequest)
                .build();
    }
}
