package org.esnack24api.esnack24adminapi.user.repository;

import org.esnack24api.esnack24adminapi.user.domain.UserEntity;
import org.esnack24api.esnack24adminapi.user.repository.search.UserSearch;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long>, UserSearch {
}
