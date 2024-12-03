package org.esnack24api.esnack24adminapi.customersupport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.esnack24api.esnack24adminapi.admin.repository.AdminRepository;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.domain.FAQEntity;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQAddDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQDetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQListDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;
import org.esnack24api.esnack24adminapi.customersupport.repository.FAQRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class FAQService {

    private final FAQRepository faqRepository;
    private final AdminRepository adminRepository;

    public PageResponseDTO<FAQListDTO> getFAQs(PageRequestDTO pageRequestDTO) {
        return faqRepository.listFAQ(pageRequestDTO);
    }


    public FAQDetailDTO getDetailFAQs(Long fno){
        return faqRepository.detailFAQ(fno);
    }

    public String addFAQ(FAQAddDTO dto) {

        Optional<AdminEntity> admno = adminRepository.findById(dto.getAdmno());

        // 관리자 존재 여부 체크
        if (!admno.isPresent()) {
            return "Admin not found with id: " + dto.getAdmno();  // 관리자 ID가 유효하지 않으면 에러 메시지 반환
        }


        // FAQEntity 객체 생성
        FAQEntity faqEntity = FAQEntity.builder()
                .admin(admno.get())  // Optional에서 값을 안전하게 가져옴
                .ftitle(dto.getFtitle())
                .fcategory(dto.getFcategory())
                .fdelete(false)
                .fcontent(dto.getFcontent())
                .build();

        // FAQ 저장
        faqRepository.save(faqEntity);

        return "success add faq";
    }

    public String deleteFAQ(Long fno) {

        Optional<FAQEntity> faq = faqRepository.findById(fno);

        if(faq.isPresent()) {

            FAQEntity faqEntity = faq.get();

            faqEntity.setFdelete(true);

            return "Successfully deleted";
        }

        return "Delete failed";
    }

    public String editFAQ(FAQDTO faqDTO) {

        Optional<FAQEntity> faq = faqRepository.findById(faqDTO.getFno());

        if(faq.isPresent()) {
            FAQEntity faqEntity = faq.get();

            faqEntity.setFtitle(faqDTO.getFtitle());
            faqEntity.setFcategory(faqDTO.getFcategory());
            faqEntity.setFcontent(faqDTO.getFcontent());

            faqRepository.save(faqEntity);
        }

        return "success edit faq";

    }



}
