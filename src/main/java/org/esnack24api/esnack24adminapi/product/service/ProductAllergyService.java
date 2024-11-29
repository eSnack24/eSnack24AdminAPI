package org.esnack24api.esnack24adminapi.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.allergy.domain.AllergyEntity;
import org.esnack24api.esnack24adminapi.product.domain.ProductAllergyEntity;
import org.esnack24api.esnack24adminapi.product.domain.ProductEntity;
import org.esnack24api.esnack24adminapi.product.repository.ProductAllergyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductAllergyService {
    private final ProductAllergyRepository productAllergyRepository;

    public void saveProductAllergies(ProductEntity product, List<Long> allergySelectList) {
        if(allergySelectList != null && !allergySelectList.isEmpty()) {
            allergySelectList.forEach(ano -> {
                ProductAllergyEntity allergyEntity = ProductAllergyEntity.builder()
                        .product(product)
                        .allergy(AllergyEntity.builder().ano(ano).build())
                        .build();
                productAllergyRepository.save(allergyEntity);
            });
        }
    }

    public void updateProductAllergies(ProductEntity product, List<Long> allergySelectList) {
        // 기존 알레르기 정보 삭제
        productAllergyRepository.deleteByProductPno(product.getPno());
        // 새로운 알레르기 정보 저장
        saveProductAllergies(product, allergySelectList);
    }

}