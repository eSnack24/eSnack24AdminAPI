package org.esnack24api.esnack24adminapi.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.community.domain.RequestAllergyEntity;
import org.esnack24api.esnack24adminapi.community.dto.RequestAllergyListDTO;
import org.esnack24api.esnack24adminapi.community.repository.RequestAllergyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class RequestAllergyService {

    private final RequestAllergyRepository requestAllergyRepository;

    public PageResponseDTO<RequestAllergyListDTO> getAllergyList(PageRequestDTO pageRequestDTO) {
        return requestAllergyRepository.getAllergyList(pageRequestDTO);
    }

    public RequestAllergyListDTO getAllergyListById(Long cano) {
        return requestAllergyRepository.getAllergyListById(cano);
    }

    public void updateAllergyAnswer(Long cano, String caanswer) {
        RequestAllergyEntity allergy = requestAllergyRepository.findById(cano)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알러지 입니다."));

        allergy.setCaanswer(caanswer);
        requestAllergyRepository.save(allergy);
    }

    public void deleteAllergy(Long cano) {
        RequestAllergyEntity allergy = requestAllergyRepository.findById(cano)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알러지입니다."));

        allergy.setCadelete(true);
        requestAllergyRepository.save(allergy);
    }
}
