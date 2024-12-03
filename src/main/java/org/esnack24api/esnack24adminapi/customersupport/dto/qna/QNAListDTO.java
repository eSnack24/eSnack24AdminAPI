package org.esnack24api.esnack24adminapi.customersupport.dto.qna;

import lombok.Data;


import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class QNAListDTO {

    private Long qno;
    private String uemail; // user
    private String admname; // admin

    private String qtitle;
    private boolean qdelete;
    private Timestamp qregdate;
    private Timestamp qmoddate;

    private boolean qstatus;

}
