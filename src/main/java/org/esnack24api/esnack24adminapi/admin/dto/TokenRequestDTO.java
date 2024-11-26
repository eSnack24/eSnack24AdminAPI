package org.esnack24api.esnack24adminapi.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequestDTO {

    @NotNull
    private String admid;

    @NotNull
    private String admpw;
}
