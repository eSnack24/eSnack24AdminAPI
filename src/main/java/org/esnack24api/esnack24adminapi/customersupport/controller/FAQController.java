package org.esnack24api.esnack24adminapi.customersupport.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.*;
import org.esnack24api.esnack24adminapi.customersupport.service.FAQService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/faq")
@Log4j2
@RequiredArgsConstructor
public class FAQController {

    private final FAQService faqService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<FAQListDTO>> faqList(PageRequestDTO pageRequestDTO){
        log.info("Retrieving faq list");


        return ResponseEntity.ok(faqService.getFAQs(pageRequestDTO));
    }

    @GetMapping("detail/{fno}")
    public ResponseEntity<FAQDetailDTO> faqDetail(@PathVariable("fno") Long fno){
        log.info("Retrieving faq list");


        return ResponseEntity.ok(faqService.getDetailFAQs(fno));
    }

    @PostMapping("add")
    public ResponseEntity<String> faqAdd(@RequestBody FAQAddDTO faqAddDTO){
        log.info("Adding faq");

        return ResponseEntity.ok(faqService.addFAQ(faqAddDTO));
    }

    @PutMapping("delete/{fno}")
    public ResponseEntity<String> faqDelete(@PathVariable("fno") Long fno){
        log.info("Deleting faq");
        return ResponseEntity.ok(faqService.deleteFAQ(fno));
    }

    @PutMapping("edit/{fno}")
    public ResponseEntity<String> faqEdit(@PathVariable("fno") Long fno, @RequestBody FAQEditDTO faqEditDTO){
        log.info("Editing faq");

        FAQDTO dto = new FAQDTO();

        dto.setFno(fno);
        dto.setFtitle(faqEditDTO.getFtitle());
        dto.setFcategory(faqEditDTO.getFcategory());
        dto.setFcontent(faqEditDTO.getFcontent());

        return ResponseEntity.ok(faqService.editFAQ(dto));

    }
}
