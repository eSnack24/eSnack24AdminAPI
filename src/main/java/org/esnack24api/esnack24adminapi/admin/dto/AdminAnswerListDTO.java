package org.esnack24api.esnack24adminapi.admin.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AdminAnswerListDTO {

    private Long qno;

    private String qtitle;

    private Timestamp qregdate;

    private Timestamp qmoddate;
}
