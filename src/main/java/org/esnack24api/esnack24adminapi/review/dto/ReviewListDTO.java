package org.esnack24api.esnack24adminapi.review.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewListDTO {

    private Long rno;
    private String ptitle_ko;
    private String uemail;
    private String rcontent;
    private boolean rdelete;

    private int rstar;
    private String rimage;
    private Timestamp rregdate;
    private Timestamp rmoddate;


}
