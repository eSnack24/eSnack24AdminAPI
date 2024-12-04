package org.esnack24api.esnack24adminapi.customersupport.dto.qna;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QNADetailDTO {

    private Long qno;

    private String qtitle;

    private String qcontent;

    private String qanswer;

    private String uemail; // user

    private String admname; // admin

    private boolean qstatus;

    private Timestamp qregdate;

    private Timestamp qmoddate;
}
