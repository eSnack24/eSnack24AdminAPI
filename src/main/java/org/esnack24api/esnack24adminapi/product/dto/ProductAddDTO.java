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
public class ProductAddDTO {
    private MultipartFile file;

    // 다국어 제품명은 PapagoAPI가 담당
    private String ptitle_ko;
    private String pcontent_ko;
    private String pcategory_ko;
    private int price;
    private int pqty;
    private String pfilename;

    // 알러지 정보 (선택된 알러지 번호 리스트)
    private List<Long> allergySelectList;
}