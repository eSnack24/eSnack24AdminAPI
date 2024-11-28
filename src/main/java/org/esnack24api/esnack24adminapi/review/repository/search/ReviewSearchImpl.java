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
}
