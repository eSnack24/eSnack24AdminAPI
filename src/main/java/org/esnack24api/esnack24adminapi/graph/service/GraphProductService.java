package org.esnack24api.esnack24adminapi.graph.service;

import lombok.RequiredArgsConstructor;
import org.esnack24api.esnack24adminapi.graph.repository.product.GraphProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GraphProductService {
    private final GraphProductRepository graphProductRepository;

    public List<Object[]> getProductCategoryCounts() {
        return graphProductRepository.productCategoryCount();
    }

    public List<Object[]> getProductAllergyCounts() {
        return graphProductRepository.productAllergyCount();
    }

    public List<Object[]> getProductAllergyDistribution() {
        return graphProductRepository.productAllergyDistribution();
    }

    public List<Object[]> getProductStockStatus() {
        return graphProductRepository.ProductStockStatus();
    }

    public List<Object[]> getProductStarCounts() {
        return graphProductRepository.productStarCount();
    }

    public List<Object[]> getProductCartCounts() {
        return graphProductRepository.productCartCount();
    }

    public List<Object[]> getProductOrderCounts() {
        return graphProductRepository.productOrderCount();
    }
}