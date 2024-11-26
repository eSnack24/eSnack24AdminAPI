package org.esnack24api.esnack24adminapi.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String pw;
}
