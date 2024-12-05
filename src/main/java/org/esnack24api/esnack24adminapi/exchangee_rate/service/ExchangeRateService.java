package org.esnack24api.esnack24adminapi.exchangee_rate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.esnack24api.esnack24adminapi.admin.repository.AdminRepository;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.exchangee_rate.domain.ExchangeRateEntity;
import org.esnack24api.esnack24adminapi.exchangee_rate.dto.*;
import org.esnack24api.esnack24adminapi.exchangee_rate.repository.ExchangeRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final AdminRepository adminRepository;

    public boolean checkUnique(CheckRateDTO checkRateDTO) {

        //true = 같은 값 있음, false = 값은 값 없음

        return exchangeRateRepository.findByTargetCurrency(checkRateDTO.getTargetCurrency()).isPresent();
    }

    public String createRate(CreateRateDTO createRateDTO) {

        String upperTarget = createRateDTO.getTargetCurrency().toUpperCase();

        CheckRateDTO checkRateDTO = new CheckRateDTO();

        checkRateDTO.setTargetCurrency(upperTarget);

        if(!checkUnique(checkRateDTO)) {

            AdminEntity admin =
                    adminRepository.findById(createRateDTO.getAdmno()).orElseThrow();

            ExchangeRateEntity exchangeRate = ExchangeRateEntity.builder()
                    .rate(createRateDTO.getRate())
                    .baseCurrency("KRW")
                    .targetCurrency(upperTarget)
                    .erupdate(Timestamp.from(Instant.now()))
                    .admin(admin)
                    .build();

            exchangeRateRepository.save(exchangeRate);

            return "Successfully created";
        }

        return "Base_Currency and Target_Currency are not unique";
    }

    public String editRate(EditRateDTO editRateDTO) {

        log.info("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
        log.info(editRateDTO);

        ExchangeRateEntity exchangeRate =
                exchangeRateRepository.findById(editRateDTO.getErno()).orElseThrow();

        log.info(exchangeRate);

        AdminEntity admin =
                adminRepository.findById(editRateDTO.getAdmno()).orElseThrow();

        log.info(admin);

        exchangeRate.setRate(editRateDTO.getRate());
        exchangeRate.setAdmin(admin);
        exchangeRate.setErupdate(Timestamp.from(Instant.now()));

        exchangeRateRepository.save(exchangeRate);

        return "Successfully updated";
    }

    public String deleteRate(DeleteRateDTO deleteRateDTO) {

        ExchangeRateEntity exchangeRate =
                exchangeRateRepository.findById(deleteRateDTO.getErno()).orElseThrow();

        AdminEntity admin =
                adminRepository.findById(deleteRateDTO.getAdmno()).orElseThrow();

        exchangeRate.setAdmin(admin);
        exchangeRate.setErupdate(Timestamp.from(Instant.now()));
        exchangeRate.setErdelete(true);

        exchangeRateRepository.save(exchangeRate);

        return "Successfully deleted";
    }

    public PageResponseDTO<RateDTO> getRateList(PageRequestDTO pageRequestDTO) {

        return exchangeRateRepository.getRateList(pageRequestDTO);
    }

    public RateDTO getRate(Long erno) {

        return exchangeRateRepository.getRate(erno);
    }
}
