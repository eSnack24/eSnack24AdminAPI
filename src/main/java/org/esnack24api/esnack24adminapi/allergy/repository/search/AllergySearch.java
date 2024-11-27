package org.esnack24api.esnack24adminapi.allergy.repository.search;

import org.esnack24api.esnack24adminapi.allergy.dto.AllergyCrudDTO;
import java.util.List;

public interface AllergySearch {
    // 알레르기 데이터는 소량이라 페이징처리 미적용
    List<AllergyCrudDTO> listAllergy();
    AllergyCrudDTO getDetailAllergy(Long ano);
}