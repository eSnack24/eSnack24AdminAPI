package org.esnack24api.esnack24adminapi.customersupport.dto.faq;

import lombok.Data;

@Data
public class FAQListDTO {

    private Long fno;
    private String admname;
    private String ftitle;
    private String fcategory;
    private boolean fdelete;
}
