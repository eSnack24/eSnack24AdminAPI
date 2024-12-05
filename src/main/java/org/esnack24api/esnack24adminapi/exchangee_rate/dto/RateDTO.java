package org.esnack24api.esnack24adminapi.exchangee_rate.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
public class RateDTO {

    private Long erno;

    private String baseCurrency;

    private String targetCurrency;

    private Timestamp erupdate;

    private BigDecimal rate;

    private String admname;
}
