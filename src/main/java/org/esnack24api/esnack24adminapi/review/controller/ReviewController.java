package org.esnack24api.esnack24adminapi.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;
import org.esnack24api.esnack24adminapi.review.dto.ReviewListDTO;
import org.esnack24api.esnack24adminapi.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/review")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ReviewListDTO>> getReviewList(PageRequestDTO pageRequestDTO) {
        log.info("getReviewList");

        return ResponseEntity.ok(reviewService.ReviewList(pageRequestDTO));

    }

    @GetMapping("detail/{rno}")
    public ResponseEntity<ReviewListDTO> getReviewOne(@PathVariable Long rno) {
        log.info("getReviewDetail - rno: " + rno);

        return ResponseEntity.ok(reviewService.ReviewOne(rno));
    }

    @DeleteMapping("/delete/{rno}")
    public ResponseEntity<String> deleteReview(@PathVariable Long rno) {
        log.info("deleteReview - rno: " + rno);
        reviewService.deleteReview(rno);
        return ResponseEntity.noContent().build();  // 삭제 성공 시 204 No Content 반환
    }

}
