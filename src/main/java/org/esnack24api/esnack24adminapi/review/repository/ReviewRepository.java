package org.esnack24api.esnack24adminapi.review.repository;

import org.esnack24api.esnack24adminapi.review.domain.ReviewEntity;
import org.esnack24api.esnack24adminapi.review.dto.ReviewListDTO;
import org.esnack24api.esnack24adminapi.review.repository.search.ReviewSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, ReviewSearch {

}
