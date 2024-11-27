package org.esnack24api.esnack24adminapi.allergy.repository;

import org.esnack24api.esnack24adminapi.allergy.domain.Allergy2Entity;
import org.esnack24api.esnack24adminapi.allergy.repository.search.AllergySearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<Allergy2Entity, Long>, AllergySearch {

}