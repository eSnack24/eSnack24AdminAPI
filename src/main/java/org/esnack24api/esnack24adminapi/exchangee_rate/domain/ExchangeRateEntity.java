package org.esnack24api.esnack24adminapi.exchangee_rate.domain;

import jakarta.persistence.*;
import lombok.*;
import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Entity
@Table(name = "tbl_exchange_rate")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"admin"})
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long erno;

    @Column(name = "base_currency")
    private String baseCurrency;

    @Column(name = "target_currency")
    private String targetCurrency;

    private BigDecimal rate;

    private Timestamp erupdate;

    private boolean erdelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admno")
    private AdminEntity admin;
}
