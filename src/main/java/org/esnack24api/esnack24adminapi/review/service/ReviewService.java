package org.esnack24api.esnack24adminapi.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.review.domain.ReviewEntity;
import org.esnack24api.esnack24adminapi.review.dto.ReviewListDTO;
import org.esnack24api.esnack24adminapi.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 리스트 조회
    @Transactional(readOnly = true)
    public PageResponseDTO<ReviewListDTO> ReviewList(PageRequestDTO pageRequestDTO) {

        log.info("Review List service");

        return reviewRepository.reviewList(pageRequestDTO);

    }

    // 리뷰 상세 조회
    @Transactional(readOnly = true)
    public ReviewListDTO ReviewOne(Long rno) {

        log.info("Review One service");

        return reviewRepository.ReviewOne(rno);
    }

    // 리뷰 논리적 삭제
    @Transactional
    public String deleteReview(Long rno) {
        log.info("deleteReview rno: " + rno);

        Optional<ReviewEntity> reviewOptional = reviewRepository.findById(rno);
        if (reviewOptional.isPresent()) {
            ReviewEntity reviewEntity = reviewOptional.get();

            reviewEntity.setRdelete(true);

            reviewRepository.save(reviewEntity);

            return "리뷰가 삭제되었습니다.";
        } else {
            return "해당 리뷰를 찾을 수 없습니다.";
        }
    }

}