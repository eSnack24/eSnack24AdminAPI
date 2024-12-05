package org.esnack24api.esnack24adminapi.user.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class UserOrderDTO {

    private Long ono;

    private Long uno;

    private BigDecimal total_amount;    //총 금액

    private String currency;    //결제 통화

    private String status;  //주문 상태(결제 완료, 대기 등)

    private String method;  //결제 수단

    private Timestamp oregdate; //주문 생성 시간

    private Timestamp ocompletedate;    //주문 완료 시간



}
