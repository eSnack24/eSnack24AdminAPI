package org.esnack24api.esnack24adminapi.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDTO extends PageRequestDTO {

    private String ptitle_ko;
    private String pcategory_ko;

}