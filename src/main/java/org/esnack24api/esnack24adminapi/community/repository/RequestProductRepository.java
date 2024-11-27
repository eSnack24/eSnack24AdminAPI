package org.esnack24api.esnack24adminapi.community.repository;

import org.esnack24api.esnack24adminapi.community.domain.RequestProductEntity;
import org.esnack24api.esnack24adminapi.community.repository.search.product.RequestProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestProductRepository extends JpaRepository<RequestProductEntity, Long>, RequestProductSearch {
}
