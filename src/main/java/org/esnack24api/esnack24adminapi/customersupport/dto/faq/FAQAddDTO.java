package org.esnack24api.esnack24adminapi.customersupport.dto.faq;

import lombok.Data;

@Data
public class FAQAddDTO {

    private Long admno;
    private String ftitle;
    private String fcategory;
    private boolean fdelete;
    private String fcontent;
}
