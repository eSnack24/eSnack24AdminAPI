package org.esnack24api.esnack24adminapi.admin.controller;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.dto.AdminDTO;
import org.esnack24api.esnack24adminapi.admin.dto.token.TokenRequestDTO;
import org.esnack24api.esnack24adminapi.admin.dto.token.TokenResponseDTO;
import org.esnack24api.esnack24adminapi.admin.exception.AdminExceptions;
import org.esnack24api.esnack24adminapi.admin.service.AdminService;
import org.esnack24api.esnack24adminapi.security.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/api/v1/jwt")
@RequiredArgsConstructor
@Log4j2
public class TokenController {

    private final AdminService adminService;

    private final JWTUtil jwtUtil;

    @Value("${org.zerock.accessTime}")
    private int accessTime;

    @Value("${org.zerock.refreshTime}")
    private int refreshTime;

    @Value("${org.zerock.alwaysNew}")
    private boolean alwaysNew;

    @PostMapping("makeToken")
    public ResponseEntity<TokenResponseDTO> makeToken(
            @RequestBody @Validated TokenRequestDTO tokenRequestDTO) {

        log.info("===============================");
        log.info("Make Token");

        AdminDTO adminDTO =
                adminService.authenticate(tokenRequestDTO.getAdmid(), tokenRequestDTO.getAdmpw());

        log.info(adminDTO);

        String admrole = adminDTO.getAdmrole();
        Long admno = adminDTO.getAdmno();

        Map<String, Object> claimMap =
                Map.of("admno", admno, "role", admrole);

        String accessToken = jwtUtil.createToken(claimMap, accessTime);
        String refreshToken = jwtUtil.createToken(claimMap, refreshTime);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(accessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setAdmno(admno);

        log.info("=-=-=-==-=-=-=-=-=-");
        log.info(tokenResponseDTO);

        return ResponseEntity.ok(tokenResponseDTO);
    }

    @PostMapping(value ="refreshToken",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDTO> refreshToken(
            @RequestHeader("Authorization") String accessToken, String refreshToken) {

        //만약 accessToken 이 없다면 혹은 refreshToken 이 없다면
        if(accessToken == null || refreshToken == null) {
            throw AdminExceptions.TOKEN_NOT_ENOUGH.get();
        }

        //accessToken 에서 Bearer(공백포함 7) 잘라낼 때 문제가 발생한다면
        if(!accessToken.startsWith("Bearer ")) {

            throw AdminExceptions.ACCESSTOKEN_TOO_SHORT.get();
        }

        String accessTokenStr = accessToken.substring("Bearer ".length());

        //AccessToken 의 만료 여부 체크
        try{

            Map<String, Object> payload = jwtUtil.validateToken(accessTokenStr);

            Long admno = ((Number) payload.get("admno")).longValue();

            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
            tokenResponseDTO.setAccessToken(accessTokenStr);
            tokenResponseDTO.setAdmno(admno);
            tokenResponseDTO.setRefreshToken(refreshToken);

            return ResponseEntity.ok(tokenResponseDTO);
        }catch(ExpiredJwtException ex){

            //정상적으로 만료된 경우

            //만약 RefreshToken 까지 만료되었다면
            try {

                Map<String, Object> payload = jwtUtil.validateToken(refreshToken);

                Long admno = ((Number) payload.get("admno")).longValue();
                String admrole = payload.get("amdrole").toString();
                String newAccessToken = null;
                String newRefreshToken = null;

                if(alwaysNew){

                    Map<String, Object> claimMap = Map.of("admno", admno, "admrole", admrole);
                    newAccessToken = jwtUtil.createToken(claimMap, accessTime);
                    newRefreshToken = jwtUtil.createToken(claimMap, refreshTime);
                }

                TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
                tokenResponseDTO.setAccessToken(newAccessToken);
                tokenResponseDTO.setRefreshToken(newRefreshToken);
                tokenResponseDTO.setAdmno(admno);

                return ResponseEntity.ok(tokenResponseDTO);
            }catch(ExpiredJwtException ex2) {

                throw AdminExceptions.REQUIRE_SIGN_IN.get();
            }
        }
    }

    @PostMapping("deleteToken")
    public ResponseEntity<TokenResponseDTO> deleteToken(
            @RequestParam Long admno) {

        Map<String, Object> claimMap =
                Map.of("admno", admno);

        String accessToken = jwtUtil.createToken(claimMap, 0);
        String refreshToken = jwtUtil.createToken(claimMap, 0);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(accessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setAdmno(admno);

        return ResponseEntity.ok(tokenResponseDTO);
    }
}
