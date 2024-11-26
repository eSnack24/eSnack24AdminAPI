package org.esnack24api.esnack24adminapi.admin.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AdminReadDTO {

    private String admid;

    private String admname;

    private String admrole;

    private Timestamp admregdate;

    private Timestamp admmoddate;

    public AdminReadDTO(String admid, String admname, String admrole, Timestamp admregdate, Timestamp admmoddate) {
        this.admid = admid;
        this.admname = admname;
        this.admrole = admrole;
        this.admregdate = admregdate;
        this.admmoddate = admmoddate;
    }
}
