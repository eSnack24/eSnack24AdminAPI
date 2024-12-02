package org.esnack24api.esnack24adminapi.review.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.domain.QNAEntity;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;
import org.esnack24api.esnack24adminapi.product.domain.QProductEntity;
import org.esnack24api.esnack24adminapi.review.domain.QReviewEntity;
import org.esnack24api.esnack24adminapi.review.domain.ReviewEntity;
import org.esnack24api.esnack24adminapi.review.dto.ReviewListDTO;
import org.esnack24api.esnack24adminapi.user.domain.QUserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.sql.Timestamp;
import java.util.List;

public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl() {
        super(ReviewEntity.class);
    }

    // 리뷰 리스트
    @Override
    public PageResponseDTO<ReviewListDTO> reviewList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("product.pno").ascending());

        QReviewEntity review = QReviewEntity.reviewEntity;
        QProductEntity product = QProductEntity.productEntity;
        QUserEntity user = QUserEntity.userEntity;

        JPQLQuery<ReviewEntity> query = from(review);

        query.leftJoin(product).on(product.eq(review.product));
        query.leftJoin(user).on(user.eq(review.user));

        query.where(review.rno.gt(0));
        query.where(review.rdelete.isFalse());

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ReviewListDTO> tupleQuery = query.select(
                Projections.bean(ReviewListDTO.class,
                        review.rno,
                        review.product.ptitle_ko,
                        review.user.uemail,
                        review.rcontent,
                        review.rstar,
                        review.rimage,
                        review.rregdate,
                        review.rmoddate

                )
        );
        List<ReviewListDTO> dtoList = tupleQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<ReviewListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    // 리뷰 상세
    @Override
    public ReviewListDTO ReviewOne(Long rno) {
        QReviewEntity review = QReviewEntity.reviewEntity;
        QProductEntity product = QProductEntity.productEntity;
        QUserEntity user = QUserEntity.userEntity;

        // 리뷰 조회를 위한 기본 쿼리 설정
        JPQLQuery<ReviewEntity> query = from(review);

        // 필요한 제품과 사용자 조인 설정
        query.leftJoin(product).on(review.product.eq(product));
        query.leftJoin(user).on(review.user.eq(user));

        // 리뷰 번호로 필터링 및 논리 삭제된 항목 제외
        query.where(review.rno.eq(rno));
        query.where(review.rdelete.isFalse());

        // DTO 매핑
        JPQLQuery<ReviewListDTO> tupleQuery = query.select(
                Projections.bean(ReviewListDTO.class,
                        review.rno,
                        review.product.ptitle_ko,
                        review.user.uemail,
                        review.rcontent,
                        review.rstar,
                        review.rimage,
                        review.rregdate,
                        review.rmoddate
                )
        );

        // 단일 결과 fetch
        ReviewListDTO reviewDetail = tupleQuery.fetchOne();

        return reviewDetail;
    }

}