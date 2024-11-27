package org.esnack24api.esnack24adminapi.customersupport.dto.qna;

import lombok.Data;

@Data
public class QNAOneDTO {

    private Long qno;

    private String qtitle;

    private String qcontent;

    private String qanswer;

    private String ptitle_ko;

    private String uemail;

    private String admname;

    private boolean qstatus;
}
