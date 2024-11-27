package org.esnack24api.esnack24adminapi.customersupport.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNADetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAAnswerDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.qna.QNAListDTO;
import org.esnack24api.esnack24adminapi.customersupport.service.QNAService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/qna")
@Log4j2
@RequiredArgsConstructor
public class QNAController {

    private final QNAService qnaService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<QNAListDTO>> getQNAList(PageRequestDTO pageRequestDTO) {
        log.info("getQNAList");

        return ResponseEntity.ok(qnaService.QNAList(pageRequestDTO));

    }

    @GetMapping("detail/{qno}")
    public ResponseEntity<QNADetailDTO> getQNADetail(@PathVariable("qno") Long qno) {
        log.info("getQNADetail");

        return ResponseEntity.ok(qnaService.QNADetail(qno));
    }

    @PutMapping("delete/{qno}")
    public ResponseEntity<String> deleteQNA(@PathVariable("qno") Long qno) {
        log.info("deleteQNA");

        return ResponseEntity.ok(qnaService.QNADelete(qno));
    }

    @PutMapping("answer/{admno}")
    public ResponseEntity<String> answerQNA(
            @PathVariable Long admno, @RequestBody QNAAnswerDTO qnaAnswerDTO) {

        return ResponseEntity.ok(qnaService.setQNAAnswer(admno, qnaAnswerDTO));
    }
}
