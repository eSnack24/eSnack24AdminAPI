package org.esnack24api.esnack24adminapi.admin.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private String admid;

    private String accessToken;

    private String refreshToken;
}
