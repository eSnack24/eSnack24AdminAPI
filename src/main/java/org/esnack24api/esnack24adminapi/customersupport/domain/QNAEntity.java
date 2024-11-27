package org.esnack24api.esnack24adminapi.customersupport.domain;

import jakarta.persistence.*;
import lombok.*;
import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.user.domain.UserEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"product", "user"})
@Table(name = "tbl_qna")
public class QNAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uno")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admno")
    private AdminEntity admin;

    private String qtitle;
    private String qcontent;
    private String qanswer;
    private String qfilename;

    @Builder.Default
    private boolean qdelete = false;

    @Builder.Default
    private boolean qstatus = false;

    @CreatedDate
    private LocalDateTime qregdate;

    @LastModifiedDate
    private LocalDateTime qmoddate;

    // 수정 메서드
    public void updateQNA(String qtitle, String qcontent, String qfilename) {
        this.qtitle = qtitle;
        this.qcontent = qcontent;
        this.qfilename = qfilename;
    }

    // 삭제 메서드
    public void deleteQNA() {
        this.qdelete = true;
    }

    public boolean getQstatus() {
        return this.qstatus;
    }
}