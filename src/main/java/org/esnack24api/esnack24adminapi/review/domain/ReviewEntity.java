package org.esnack24api.esnack24adminapi.review.domain;


import jakarta.persistence.*;
import lombok.*;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.user.domain.UserEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"user", "product"})
@Table(name = "tbl_review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uno")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private ProductEntity product;

    private String rcontent;
    private boolean rdelete;
    private int rstar;
    private String rimage;

    private Timestamp rmoddate;
    private Timestamp rregdate;
}