package org.esnack24api.esnack24adminapi.allergy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.allergy.dto.AllergyCrudDTO;
import org.esnack24api.esnack24adminapi.allergy.service.AllergyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/allergy")
@Log4j2
@RequiredArgsConstructor
public class AllergyController {

    private final AllergyService allergyService;

    @GetMapping("list")
    public ResponseEntity<List<AllergyCrudDTO>> allergyList() {
        log.info("Retrieving allergy list");
        return ResponseEntity.ok(allergyService.getListAllergy());
    }

    @GetMapping("detail/{ano}")
    public ResponseEntity<AllergyCrudDTO> allergyDetail(@PathVariable("ano") Long ano) {
        log.info("Retrieving allergy detail");
        return ResponseEntity.ok(allergyService.getDetailAllergy(ano));
    }

    @PostMapping("add")
    public ResponseEntity<String> allergyAdd(@RequestBody AllergyCrudDTO allergyCrudDTO) {
        log.info("Adding allergy");
        return ResponseEntity.ok(allergyService.addAllergy(allergyCrudDTO));
    }

    @DeleteMapping("delete/{ano}")
    public ResponseEntity<String> allergyDelete(@PathVariable("ano") Long ano) {
        log.info("Deleting allergy");
        return ResponseEntity.ok(allergyService.deleteAllergy(ano));
    }

    @PutMapping("edit/{ano}")
    public ResponseEntity<String> allergyEdit(@PathVariable("ano") Long ano, @RequestBody AllergyCrudDTO allergyCrudDTO) {
        log.info("Editing allergy");
        return ResponseEntity.ok(allergyService.editAllergy(ano, allergyCrudDTO));
    }
}