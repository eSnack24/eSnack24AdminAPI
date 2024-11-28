package org.esnack24api.esnack24adminapi.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.review.dto.ReviewListDTO;
import org.esnack24api.esnack24adminapi.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public PageResponseDTO<ReviewListDTO> ReviewList(PageRequestDTO pageRequestDTO) {

        log.info("Review List service");

        return reviewRepository.reviewList(pageRequestDTO);

    }
}
