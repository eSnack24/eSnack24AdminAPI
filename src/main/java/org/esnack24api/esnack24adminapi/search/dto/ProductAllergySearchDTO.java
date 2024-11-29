package org.esnack24api.esnack24adminapi.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAllergySearchDTO extends PageRequestDTO {

    private List<Long> allergySelectList;
}