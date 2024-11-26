package org.esnack24api.esnack24adminapi.admin.controller;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.dto.*;
import org.esnack24api.esnack24adminapi.admin.exception.AdminExceptions;
import org.esnack24api.esnack24adminapi.admin.service.AdminService;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.security.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminController {

    private final AdminService adminService;


    @PostMapping("add")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRegisterDTO adminRegisterDTO) {

        return ResponseEntity.ok(adminService.registerAdminService(adminRegisterDTO));
    }

    @PutMapping("edit/{admno}")
    public ResponseEntity<String> editAdmin(@PathVariable Long admno, @RequestBody AdminEditDTO adminEditDTO) {

        AdminDTO dto = new AdminDTO();

        dto.setAdmno(admno);
        dto.setAdmid(adminEditDTO.getAdmid());
        dto.setAdmpw(adminEditDTO.getAdmpw());
        dto.setAdmrole(adminEditDTO.getAdmrole());
        dto.setAdmname(adminEditDTO.getAdmname());

        return ResponseEntity.ok(adminService.editAdminService(dto));
    }

    @DeleteMapping("delete/{admno}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long admno) {

        return ResponseEntity.ok(adminService.deleteAdminService(admno));
    }

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<AdminListDTO>> allAdminList(
            PageRequestDTO pageRequestDTO) {

        return ResponseEntity.ok(adminService.getAdminList(pageRequestDTO));
    }

    @GetMapping("roleList/{role}")
    public ResponseEntity<PageResponseDTO<AdminListDTO>> adminListByRole(
            @PathVariable String role, PageRequestDTO pageRequestDTO) {

        return ResponseEntity.ok(adminService.getAdminListByRole(role, pageRequestDTO));
    }


}
