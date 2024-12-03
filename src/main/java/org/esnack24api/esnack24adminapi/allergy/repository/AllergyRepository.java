package org.esnack24api.esnack24adminapi.allergy.repository;

import org.esnack24api.esnack24adminapi.allergy.domain.AllergyEntity;
import org.esnack24api.esnack24adminapi.allergy.repository.search.AllergySearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<AllergyEntity, Long>, AllergySearch {

}