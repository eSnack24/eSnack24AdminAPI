package org.esnack24api.esnack24adminapi.exchangee_rate.repository;

import org.esnack24api.esnack24adminapi.exchangee_rate.domain.ExchangeRateEntity;
import org.esnack24api.esnack24adminapi.exchangee_rate.repository.search.ExchangeRateSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long>, ExchangeRateSearch {

    Optional<ExchangeRateEntity> findByBaseCurrency(String base_currency);
}
