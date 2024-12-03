package org.esnack24api.esnack24adminapi.admin.dto;

import lombok.Data;
import org.esnack24api.esnack24adminapi.admin.domain.AdminRole;

@Data
public class AdminRegisterDTO {

    private String admid;

    private String admpw;

    private String admrole;

    private String admname;
}
