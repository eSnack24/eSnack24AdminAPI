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
public class RequestAllergyListDTO {

    private Long cano;

    private String caallergy;

    private String catitle;

    private String caanswer;

    private boolean cadelete;

    private Timestamp caregdate;

    private Timestamp camoddate;

    private boolean castatus;

}
