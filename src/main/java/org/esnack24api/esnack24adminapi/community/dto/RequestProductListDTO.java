package org.esnack24api.esnack24adminapi.community.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestProductListDTO {

    private Long cpno;

    private String cpproduct;

    private String cptitle;

    private String cpanswer;

    private Timestamp cpregdate;

    private Timestamp cpmoddate;

    private boolean cpdelete;

}
