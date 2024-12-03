package org.esnack24api.esnack24adminapi.product.repository;

import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.product.repository.search.ProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductSearch {

    Optional<ProductEntity> findByPno(Long pno);
}
