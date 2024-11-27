package org.esnack24api.esnack24adminapi.allergy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyCrudDTO {
    private Long ano;

    private String atitle_ko;
    private String atitle_en;
    private String atitle_ja;
    private String atitle_zh;
}