package org.esnack24api.esnack24adminapi.admin.dto.token;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private String admid;

    private String accessToken;

    private String refreshToken;
}
