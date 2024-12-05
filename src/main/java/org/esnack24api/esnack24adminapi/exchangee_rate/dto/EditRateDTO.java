package org.esnack24api.esnack24adminapi.exchangee_rate.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EditRateDTO {

    private Long erno;

    private Long admno;

    private BigDecimal rate;
}
