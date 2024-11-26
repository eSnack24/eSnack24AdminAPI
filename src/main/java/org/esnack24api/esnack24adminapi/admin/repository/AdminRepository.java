package org.esnack24api.esnack24adminapi.admin.repository;

import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    Optional<AdminEntity> findByAdmid(String admid);
}
