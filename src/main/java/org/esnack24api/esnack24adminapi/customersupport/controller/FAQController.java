package org.esnack24api.esnack24adminapi.customersupport.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.customersupport.dto.FAQAddDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.FAQDetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.FAQListDTO;
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
    public ResponseEntity<List<FAQListDTO>> faqList(){
        log.info("Retrieving faq list");


        return ResponseEntity.ok(faqService.getFAQs());
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
}
