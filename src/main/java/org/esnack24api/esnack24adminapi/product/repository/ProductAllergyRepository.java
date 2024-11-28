package org.esnack24api.esnack24adminapi.product.repository;

import org.esnack24api.esnack24adminapi.product.domain.ProductAllergyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductAllergyRepository extends JpaRepository<ProductAllergyEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductAllergyEntity pa WHERE pa.product.pno = :pno")
    void deleteByProductPno(@Param("pno") Long pno);

}