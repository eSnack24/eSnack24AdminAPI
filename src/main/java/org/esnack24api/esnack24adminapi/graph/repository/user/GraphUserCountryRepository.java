package org.esnack24api.esnack24adminapi.graph.repository.user;

import org.esnack24api.esnack24adminapi.user.domain.UserCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GraphUserCountryRepository extends JpaRepository<UserCountryEntity,Long> {

    @Query("""
    select c.country, count(c)
    from UserCountryEntity c
    group by c.country
    """)
    List<Object[]> countByCountry();

}
