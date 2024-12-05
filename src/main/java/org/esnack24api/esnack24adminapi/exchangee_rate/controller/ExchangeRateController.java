package org.esnack24api.esnack24adminapi.exchangee_rate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.exchangee_rate.dto.*;
import org.esnack24api.esnack24adminapi.exchangee_rate.service.ExchangeRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/exchange_rate")
@RequiredArgsConstructor
@Log4j2
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("check")
    public ResponseEntity<Boolean> checkExchangeRate(@RequestParam String targetCurrency) {

        CheckRateDTO checkRateDTO = new CheckRateDTO();
        checkRateDTO.setTargetCurrency(targetCurrency);

        return ResponseEntity.ok(exchangeRateService.checkUnique(checkRateDTO));
    }

    @PostMapping("create")
    public ResponseEntity<String> createExchangeRate(@RequestBody CreateRateDTO createRateDTO) {

        return ResponseEntity.ok(exchangeRateService.createRate(createRateDTO));
    }

    @PutMapping("edit")
    public ResponseEntity<String> editExchangeRate(@RequestBody EditRateDTO editRateDTO) {

        return ResponseEntity.ok(exchangeRateService.editRate(editRateDTO));
    }

    @PutMapping("delete")
    public ResponseEntity<String> deleteExchangeRate(@RequestBody DeleteRateDTO deleteRateDTO) {

        return ResponseEntity.ok(exchangeRateService.deleteRate(deleteRateDTO));
    }

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<RateDTO>> getRateList(PageRequestDTO pageRequestDTO) {

        return ResponseEntity.ok(exchangeRateService.getRateList(pageRequestDTO));
    }

    @GetMapping("read/{erno}")
    public ResponseEntity<RateDTO> getRate(@PathVariable Long erno) {

        return ResponseEntity.ok(exchangeRateService.getRate(erno));
    }
}
