package org.esnack24api.esnack24adminapi.community.repository;

import org.esnack24api.esnack24adminapi.community.domain.RequestAllergyEntity;
import org.esnack24api.esnack24adminapi.community.repository.search.allergy.RequestAllergySearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestAllergyRepository extends JpaRepository<RequestAllergyEntity, Long>, RequestAllergySearch {
}
