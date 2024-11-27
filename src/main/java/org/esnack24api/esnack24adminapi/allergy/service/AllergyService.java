package org.esnack24api.esnack24adminapi.allergy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.allergy.domain.Allergy2Entity;
import org.esnack24api.esnack24adminapi.allergy.dto.AllergyCrudDTO;
import org.esnack24api.esnack24adminapi.allergy.repository.AllergyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class AllergyService {

    private final AllergyRepository allergyRepository;

    // 알레르기 전체 목록 조회
    @Transactional(readOnly = true)
    public List<AllergyCrudDTO> getListAllergy() {
        return allergyRepository.listAllergy();
    }

    // 특정 알레르기 상세 조회
    @Transactional(readOnly = true)
    public AllergyCrudDTO getDetailAllergy(Long ano) {
        return allergyRepository.getDetailAllergy(ano);
    }

    // 알레르기 생성
    @Transactional
    public String addAllergy(AllergyCrudDTO dto) {
        Allergy2Entity entity = Allergy2Entity.builder()
                .atitle_ko(dto.getAtitle_ko())
                .atitle_en(dto.getAtitle_en())
                .atitle_ja(dto.getAtitle_ja())
                .atitle_zh(dto.getAtitle_zh())
                .build();

        allergyRepository.save(entity);
        return "Success add allergy";
    }

    // 알레르기 수정
    @Transactional
    public String editAllergy(Long ano, AllergyCrudDTO dto) {
        Optional<Allergy2Entity> allergyOptional = allergyRepository.findById(ano);

        if (allergyOptional.isPresent()) {
            Allergy2Entity entity = allergyOptional.get();
            entity.setAtitle_ko(dto.getAtitle_ko());
            entity.setAtitle_en(dto.getAtitle_en());
            entity.setAtitle_ja(dto.getAtitle_ja());
            entity.setAtitle_zh(dto.getAtitle_zh());

            allergyRepository.save(entity);
            return "Allergy Successfully updated";
        }

        return "Allergy Edit failed";
    }

    // 알레르기 삭제
    @Transactional
    public String deleteAllergy(Long ano) {
        Optional<Allergy2Entity> allergyOptional = allergyRepository.findById(ano);

        if (allergyOptional.isPresent()) {
            allergyRepository.delete(allergyOptional.get());
            return "Allergy Successfully deleted";
        }

        return "Allergy Delete failed";
    }
}