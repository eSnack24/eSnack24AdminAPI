package org.esnack24api.esnack24adminapi.customersupport.repository;

import org.esnack24api.esnack24adminapi.customersupport.domain.FAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.repository.search.FAQSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQEntity, Long>, FAQSearch {
}
