package org.esnack24api.esnack24adminapi.community.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.community.dto.RequestAllergyListDTO;
import org.esnack24api.esnack24adminapi.community.service.RequestAllergyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/api/v1/request/allergy")
@Log4j2
@RequiredArgsConstructor
public class RequestAllergyController {
    private final RequestAllergyService requestAllergyService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<RequestAllergyListDTO>> getAllergyList(PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(requestAllergyService.getAllergyList(pageRequestDTO));
    }

    @GetMapping("detail/{cano}")
    public ResponseEntity<RequestAllergyListDTO> getAllergyDetail(@PathVariable("cano") Long cano) {
        return ResponseEntity.ok(requestAllergyService.getAllergyListById(cano));
    }

    @PutMapping("answer/{cano}")
    public ResponseEntity<String> updateAllergyAnswer(
            @PathVariable Long cano,
            @RequestBody String caanswer) {

     requestAllergyService.updateAllergyAnswer(cano, caanswer);

     return ResponseEntity.ok("답변이 성공적으로 등록/수정 되었습니다.");

    }

    @DeleteMapping("delete/{cano}")
    public ResponseEntity<String> deleteAllergy(@PathVariable Long cano) {
        requestAllergyService.deleteAllergy(cano);
        return ResponseEntity.ok("글이 성공적으로 삭제 되었습니다.");
    }

    @GetMapping("statuslist/{castatus}")
    public ResponseEntity<PageResponseDTO<RequestAllergyListDTO>> getAllergyStatusList(
            @PathVariable("castatus") boolean castatus, PageRequestDTO pageRequestDTO) {

        return ResponseEntity.ok(requestAllergyService.RequestAllergyStatusList(castatus, pageRequestDTO));
    }


}
