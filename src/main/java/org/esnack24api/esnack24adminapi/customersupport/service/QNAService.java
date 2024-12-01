package org.esnack24api.esnack24adminapi.customersupport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.esnack24api.esnack24adminapi.admin.dto.AdminWorkListDTO;
import org.esnack24api.esnack24adminapi.admin.repository.AdminRepository;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.customersupport.domain.QNAEntity;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNADetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAAnswerDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;
import org.esnack24api.esnack24adminapi.customersupport.repository.QNARepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class QNAService {

    private final QNARepository qnaRepository;

    private final AdminRepository adminRepository;


    public PageResponseDTO<QNAListDTO> QNAList(PageRequestDTO pageRequestDTO) {

        log.info("QNA List service");

        return qnaRepository.qnaList(pageRequestDTO);

    }

    public String setQNAAnswer(Long admno, QNAAnswerDTO qnaAnswerDTO) {

        QNAEntity qna = qnaRepository.findById(qnaAnswerDTO.getQno()).orElseThrow();

        AdminEntity admin = adminRepository.findById(admno).orElseThrow();

        qna.setAdmin(admin);
        qna.setQanswer(qnaAnswerDTO.getQanswer());
        qna.setQstatus(true);
        qna.setQmoddate(Timestamp.from(Instant.now()));

        qnaRepository.save(qna);

        return "Successfully updated QNA answer";
    }

    public QNADetailDTO QNADetail(Long qno) {

        log.info("QNA Detail service");

        return qnaRepository.qnaDetail(qno);
    }

    public String QNADelete(Long qno) {
        log.info("QNA Delete service");

        Optional<QNAEntity> qna = qnaRepository.findById(qno);

        if (qna.isPresent()) {
            QNAEntity qnaEntity = qna.get();

            qnaEntity.setQdelete(true);

            return "QNA deleted";
        }
        return "QNA not found";
    }

    public PageResponseDTO<QNAListDTO> QNAStatusList(Boolean status, PageRequestDTO pageRequestDTO) {

        log.info("QNA List service");

        return qnaRepository.qnaStatusList(status, pageRequestDTO);

    }

}
