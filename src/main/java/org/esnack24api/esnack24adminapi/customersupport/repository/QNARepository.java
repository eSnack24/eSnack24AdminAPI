package org.esnack24api.esnack24adminapi.customersupport.repository;

import org.esnack24api.esnack24adminapi.customersupport.domain.QNAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QNARepository extends JpaRepository<QNAEntity, Long> {
}
