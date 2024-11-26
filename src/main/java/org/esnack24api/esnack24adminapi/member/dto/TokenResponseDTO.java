package org.esnack24api.esnack24adminapi.member.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private String email;

    private String accessToken;

    private String refreshToken;
}
