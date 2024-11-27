package org.esnack24api.esnack24adminapi.graph.repository.user;

import org.esnack24api.esnack24adminapi.user.domain.UserAllergyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GraphUserAllergyRepository extends JpaRepository<UserAllergyEntity, Long> {

    @Query("""
            select a.atitle_ko, count(ua)
            from UserAllergyEntity ua
            join ua.allergy a
            group by a.atitle_ko
            """)
    List<Object[]> countUserAllergy();

}
