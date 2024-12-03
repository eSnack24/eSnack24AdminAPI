package org.esnack24api.esnack24adminapi.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEditDTO {

    private Long pno;
    private int price;
    private int pqty;
    private String pfilename;

    private String ptitle_ko;
    private String pcontent_ko;
    private String pcategory_ko;

    private String ptitle_en;
    private String pcontent_en;
    private String pcategory_en;

    private String ptitle_ja;
    private String pcontent_ja;
    private String pcategory_ja;

    private String ptitle_zh;
    private String pcontent_zh;
    private String pcategory_zh;

    private List<Long> allergySelectList;
}
