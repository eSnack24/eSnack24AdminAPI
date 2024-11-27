package org.esnack24api.esnack24adminapi.product.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductListDTO {

    private Long pno;
    private int price;
    private int pqty;
    private boolean pdelete;
    private String pfilename;

    private Timestamp pregdate;
    private Timestamp pmoddate;

    private String ptitle_ko;
    private String pcontent_ko;
    private String pcategory_ko;

}
