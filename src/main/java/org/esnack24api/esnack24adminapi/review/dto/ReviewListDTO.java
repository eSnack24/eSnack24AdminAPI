package org.esnack24api.esnack24adminapi.review.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewListDTO {

    private Long rno;
    private String ptitle_ko; // product
    private String uemail; // user
    private String rcontent;

    private int rstar;
    private String rimage;
    private Timestamp rregdate;
    private Timestamp rmoddate;


}
