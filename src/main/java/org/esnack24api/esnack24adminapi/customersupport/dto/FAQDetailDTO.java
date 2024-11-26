package org.esnack24api.esnack24adminapi.customersupport.dto;

import lombok.Data;

@Data
public class FAQDetailDTO {

    private Long fno;
    private Long admno;
    private String ftitle;
    private String fcategory;
    private boolean fdelete;
    private String fcontent;

}
