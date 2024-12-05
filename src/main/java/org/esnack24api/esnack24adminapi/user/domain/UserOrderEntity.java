package org.esnack24api.esnack24adminapi.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user"})
@Table(name = "tbl_order")
public class UserOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ono;   //primary key

    @Column(name = "transaction_id")
    private String transactionId;   //paypalOrderId

    @Column(name = "toss_payment_key")
    private String tossPaymentKey;  //toss 의 paymentKey

    private BigDecimal total_amount;    //총 금액

    private String currency;    //결제 통화

    private String status;  //주문 상태(결제 완료, 대기 등)

    private String method;  //결제 수단

    private Timestamp oregdate; //주문 생성 시간

    private Timestamp ocompletedate;    //주문 완료 시간

    @ManyToOne
    @JoinColumn(name = "uno")
    private UserEntity user;    //user primary key
}
