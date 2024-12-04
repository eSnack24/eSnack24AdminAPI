package org.esnack24api.esnack24adminapi.graph.repository.user;

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
}
