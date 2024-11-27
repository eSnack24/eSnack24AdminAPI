package org.esnack24api.esnack24adminapi.customersupport.dto.qna;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QNAListDTO {

    private Long qno;
    private String uemail; // user
    private String ptitle_ko; // product
    private String adname; // admin

    private String qtitle;
    private boolean qdelete;
    private Timestamp qregdate;
    private Timestamp qmoddate;

    private boolean qstatus;

}
