package org.esnack24api.esnack24adminapi.admin.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AdminListDTO {

    private Long admno;

    private String admid;

    private String admpw;

    private String admrole;

    private Timestamp admregdate;

    private Timestamp admmoddate;
}
