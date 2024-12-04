package org.esnack24api.esnack24adminapi.community.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.common.dto.PageResponseDTO;
import org.esnack24api.esnack24adminapi.community.domain.RequestProductEntity;
import org.esnack24api.esnack24adminapi.community.dto.RequestAllergyListDTO;
import org.esnack24api.esnack24adminapi.community.dto.RequestProductListDTO;
import org.esnack24api.esnack24adminapi.community.repository.RequestProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class RequestProductService {

    private final RequestProductRepository requestProductRepository;

    public PageResponseDTO<RequestProductListDTO> getProductList(PageRequestDTO pageRequestDTO) {

        return requestProductRepository.getRProductList(pageRequestDTO);
    }

    public RequestProductListDTO getProductById(Long cpno) {
        return requestProductRepository.getProductById(cpno);
    }

    public void updateProductAnswer(Long cpno, String cpanswer) {
        RequestProductEntity product = requestProductRepository.findById(cpno)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setCpanswer(cpanswer);
        product.setCpstatus(true);
        requestProductRepository.save(product);
    }

    public void deleteProduct(Long cpno) {
        RequestProductEntity product = requestProductRepository.findById(cpno)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setCpdelete(true);
        requestProductRepository.save(product);
    }

    public PageResponseDTO<RequestProductListDTO> RequestProductStatusList(Boolean status, PageRequestDTO pageRequestDTO) {

        return requestProductRepository.getTFProductList(status, pageRequestDTO);
    }


}
