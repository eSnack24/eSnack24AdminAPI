package org.esnack24api.esnack24adminapi.graph.repository.user;

import org.esnack24api.esnack24adminapi.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GraphUserBirthRepository extends JpaRepository<UserEntity,Long> {

    @Query("""
            select u.ubirth, count(u) from UserEntity u
            group by u.ubirth
            """)
    List<Object[]> countUserBirth();

}
