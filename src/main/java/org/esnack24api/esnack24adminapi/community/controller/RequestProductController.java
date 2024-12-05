package org.esnack24api.esnack24adminapi.community.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.community.dto.RequestAllergyListDTO;
import org.esnack24api.esnack24adminapi.community.dto.RequestProductListDTO;
import org.esnack24api.esnack24adminapi.community.service.RequestProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/api/v1/request/product")
@Log4j2
@RequiredArgsConstructor
public class RequestProductController {

    private final RequestProductService requestProductService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<RequestProductListDTO>> getProductList(PageRequestDTO pageRequestDTO) {

        return ResponseEntity.ok(requestProductService.getProductList(pageRequestDTO));
    }

    @GetMapping("detail/{cpno}")
    public ResponseEntity<RequestProductListDTO> getProductById(@PathVariable Long cpno) {

        return ResponseEntity.ok(requestProductService.getProductById(cpno));
    }

    @PutMapping("answer/{cpno}")
    public ResponseEntity<String> updateProductAnswer(
            @PathVariable Long cpno,
            @RequestBody String cpanswer) {

        requestProductService.updateProductAnswer(cpno, cpanswer);
        return ResponseEntity.ok("답변이 성공적으로 등록/수정되었습니다.");
    }

    @DeleteMapping("delete/{cpno}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long cpno) {
        requestProductService.deleteProduct(cpno);
        return ResponseEntity.ok("글이 성공적으로 삭제되었습니다.");


    }

    @GetMapping("statuslist/{cpstatus}")
    public ResponseEntity<PageResponseDTO<RequestProductListDTO>> getProductStatusList(
            @PathVariable("cpstatus") boolean cpstatus, PageRequestDTO pageRequestDTO) {

        return ResponseEntity.ok(requestProductService.RequestProductStatusList(cpstatus, pageRequestDTO));
    }


}
