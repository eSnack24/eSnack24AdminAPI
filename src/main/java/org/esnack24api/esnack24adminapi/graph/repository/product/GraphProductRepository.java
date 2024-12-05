package org.esnack24api.esnack24adminapi.graph.repository.product;

import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphProductRepository extends JpaRepository<ProductEntity, Long> {
    // 카테고리별 제품 수
    @Query("""
        select p.pcategory_ko, count(p)
        from ProductEntity p 
        where p.pdelete = false 
        group by p.pcategory_ko
    """)
    List<Object[]> productCategoryCount();

    // 알레르기 성분별 제품 수
    @Query("""
        select a.atitle_ko, count(pa)
        from ProductAllergyEntity pa 
        join pa.allergy a 
        group by a.atitle_ko
    """)
    List<Object[]> productAllergyCount();

    // 알레르기 정보에 따른 제품 분포
    @Query("""
        select a.atitle_ko, 
               count(distinct p.pno) as product_count, 
               (count(distinct p.pno) * 100.0 / 
                (select count(distinct pno) from ProductEntity)) as percentage
        from ProductAllergyEntity pa 
        join pa.allergy a
        join pa.product p
        group by a.atitle_ko
        order by product_count desc
    """)
    List<Object[]> productAllergyDistribution();

    // 재고 상태
    @Query("""
    select 
        case 
            when p.pqty = 0 then '품절'
            when p.pqty <= 10 then '재고 부족'
            when p.pqty <= 50 then '낮은 재고'
            else '충분한 재고'
        end as stock_status, 
        count(p) as product_count
    from ProductEntity p
    group by 
        case 
            when p.pqty = 0 then '품절'
            when p.pqty <= 10 then '재고 부족'
            when p.pqty <= 50 then '낮은 재고'
            else '충분한 재고'
        end
    """)
    List<Object[]> ProductStockStatus();

    // 별점 통계
    @Query("""
        select r.rstar, count(r) 
        from ReviewEntity r 
        group by r.rstar
    """)
    List<Object[]> productStarCount();

    // 장바구니 통계
    @Query("""
    select p.pcategory_ko, count(ci) 
    from GraphCartItemEntity ci 
    join ProductEntity p on ci.pno = p.pno 
    group by p.pcategory_ko
    """)
    List<Object[]> productCartCount();

    // 구매 통계
    @Query("""
    select p.pcategory_ko, sum(oi.oiqty) 
    from GraphOrderItemEntity oi 
    join ProductEntity p on oi.pno = p.pno 
    group by p.pcategory_ko 
    order by sum(oi.oiqty) desc
    """)
    List<Object[]> productOrderCount();
}
