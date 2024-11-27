package org.esnack24api.esnack24adminapi.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAllergyListDTO {
    private Long pno;
    private String ptitle_ko;
    private String pcategory_ko;
    private int pqty;
    private int price;
    private String pfilename;
    private Timestamp pregdate;
    private Timestamp pmoddate;
    private String allergyInfo;
}