package org.esnack24api.esnack24adminapi.graph.service;

import lombok.RequiredArgsConstructor;
import org.esnack24api.esnack24adminapi.graph.repository.user.GraphProductRepository;
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
}