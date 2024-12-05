
package org.esnack24api.esnack24adminapi.admin.service;

import com.google.api.client.auth.oauth2.TokenRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.domain.AdminEntity;
import org.esnack24api.esnack24adminapi.admin.dto.*;
import org.esnack24api.esnack24adminapi.admin.exception.AdminExceptions;
import org.esnack24api.esnack24adminapi.admin.repository.AdminRepository;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.common.exception.CommonExceptions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;


    public AdminDTO authenticate(String id, String password) {

        Optional<AdminEntity> result = adminRepository.findByAdmid(id);

        AdminEntity admin = result.orElseThrow(() -> AdminExceptions.BAD_AUTH.get());

        String enPw = admin.getAdmpw();

        boolean match = passwordEncoder.matches(password, enPw);

        if(!match) {
            throw CommonExceptions.READ_ERROR.get();
        }

        AdminDTO AdminDTO = new AdminDTO();
        AdminDTO.setAdmno(admin.getAdmno());
        AdminDTO.setAdmid(id);
        AdminDTO.setAdmpw(enPw);
        AdminDTO.setAdmrole(admin.getAdmrole().toString());

        return AdminDTO;
    }

    public String registerAdminService(AdminRegisterDTO adminRegisterDTO) {

        AdminEntity admin = AdminEntity.builder()
                .admid(adminRegisterDTO.getAdmid())
                .admpw(passwordEncoder.encode(adminRegisterDTO.getAdmpw()))
                .admrole(adminRegisterDTO.getAdmrole())
                .admname(adminRegisterDTO.getAdmname())
                .build();

        adminRepository.save(admin);

        return "Successfully registered";
    }

    public String editAdminService(AdminDTO adminDTO) {

        Optional<AdminEntity> admin = adminRepository.findById(adminDTO.getAdmno());

        if (admin.isPresent()) {
            AdminEntity adminEntity = admin.get();

            if (adminDTO.getAdmid() != null) {
                adminEntity.setAdmid(adminDTO.getAdmid());
            }
            if (adminDTO.getAdmpw() != null) {
                adminEntity.setAdmpw(passwordEncoder.encode(adminDTO.getAdmpw()));
            }
            if (adminDTO.getAdmrole() != null) {
                adminEntity.setAdmrole(adminDTO.getAdmrole());
            }
            if (adminDTO.getAdmname() != null) {
                adminEntity.setAdmname(adminDTO.getAdmname());
            }

            adminEntity.setAdmmoddate(Timestamp.from(Instant.now()));

            adminRepository.save(adminEntity);

            return "Successfully updated";
        }

        return "Update failed";
    }


    public String deleteAdminService(Long amdno) {

        Optional<AdminEntity> admin = adminRepository.findById(amdno);

        if(admin.isPresent()) {

            AdminEntity adminEntity = admin.get();

            adminEntity.setAdmdelete(true);

            adminRepository.save(adminEntity);

            return "Successfully deleted";
        }

        return "Delete failed";
    }

    public AdminReadDTO getAdminOne(Long admno) {

        return adminRepository.getAdmin(admno);
    }

    public PageResponseDTO<AdminListDTO> getAdminList(PageRequestDTO pageRequestDTO) {

        return adminRepository.adminList(pageRequestDTO);
    }

    public PageResponseDTO<AdminListDTO> getAdminListByRole(String role, PageRequestDTO pageRequestDTO) {

        return adminRepository.adminListByRole(role, pageRequestDTO);
    }

    public PageResponseDTO<AdminWorkListDTO> getAdminWorkList(String order_by, PageRequestDTO pageRequestDTO) {

        return adminRepository.adminWorkList(order_by, pageRequestDTO);
    }

    public PageResponseDTO<AdminAnswerListDTO> adminAnswerList(Long admno, PageRequestDTO pageRequestDTO) {

        return adminRepository.adminAnswerList(admno, pageRequestDTO);
    }

    public String updateToken(FCMTokenRequestDTO tokenRequest) {
        // 로그인한 관리자의 ID로 해당 관리자 찾기
        Optional<AdminEntity> adminOptional = adminRepository.findById(tokenRequest.getAdmno());

        if (adminOptional.isPresent()) {
            // 관리자 엔티티 가져오기
            AdminEntity admin = adminOptional.get();

            // 기존의 token을 null로 초기화
            admin.setToken(null); // 기존 토큰 지우기

            // 새로 받은 token으로 갱신
            admin.setToken(tokenRequest.getToken());

            // 변경된 admin 저장
            adminRepository.save(admin);

            return "Successfully token updated";
        }

        return "Admin not found";
    }
}