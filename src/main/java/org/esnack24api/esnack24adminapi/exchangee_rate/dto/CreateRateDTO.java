package org.esnack24api.esnack24adminapi.exchangee_rate.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateRateDTO {

    private String baseCurrency;

    private String targetCurrency;

    private Long admno;

    private BigDecimal rate;
}
