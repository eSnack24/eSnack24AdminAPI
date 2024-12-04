package org.esnack24api.esnack24adminapi.graph.controller;

import lombok.RequiredArgsConstructor;
import org.esnack24api.esnack24adminapi.graph.service.GraphProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/api/v1/graph/product")
@RequiredArgsConstructor
public class GraphProductController {
    private final GraphProductService graphProductService;

    @GetMapping("category")
    public ResponseEntity<Map<String, Long>> getCategoryCount() {
        List<Object[]> results = graphProductService.getProductCategoryCounts();
        Map<String, Long> categoryCounts = new HashMap<>();

        for (Object[] row : results) {
            String category = (String) row[0];
            Long count = (Long) row[1];
            categoryCounts.put(category, count);
        }

        return ResponseEntity.ok(categoryCounts);
    }

    @GetMapping("allergy")
    public ResponseEntity<Map<String, Long>> getAllergyCount() {
        List<Object[]> results = graphProductService.getProductAllergyCounts();
        Map<String, Long> allergyCounts = new HashMap<>();

        for (Object[] row : results) {
            String allergy = (String) row[0];
            Long count = (Long) row[1];
            allergyCounts.put(allergy, count);
        }

        return ResponseEntity.ok(allergyCounts);
    }

    @GetMapping("allergy-distribution")
    public ResponseEntity<List<Map<String, Object>>> getAllergyDistribution() {
        List<Object[]> results = graphProductService.getProductAllergyDistribution();
        List<Map<String, Object>> distributions = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> distribution = new HashMap<>();
            distribution.put("allergyName", row[0]);
            distribution.put("productCount", row[1]);
            distribution.put("percentage", row[2]);
            distributions.add(distribution);
        }

        return ResponseEntity.ok(distributions);
    }

    @GetMapping("stock-status")
    public ResponseEntity<Map<String, Long>> getStockStatus() {
        List<Object[]> results = graphProductService.getProductStockStatus();
        Map<String, Long> stockStatus = new HashMap<>();

        for (Object[] row : results) {
            String status = (String) row[0];
            Long count = (Long) row[1];
            stockStatus.put(status, count);
        }

        return ResponseEntity.ok(stockStatus);
    }

    @GetMapping("star-count")
    public ResponseEntity<Map<String, Long>> getStarCount() {
        List<Object[]> results = graphProductService.getProductStarCounts();
        Map<String, Long> starCounts = new HashMap<>();

        for (Object[] row : results) {
            String star = String.valueOf(row[0]);
            Long count = (Long) row[1];
            starCounts.put(star, count);
        }

        return ResponseEntity.ok(starCounts);
    }

    @GetMapping("cart-count")
    public ResponseEntity<Map<String, Long>> getCartCount() {
        List<Object[]> results = graphProductService.getProductCartCounts();
        Map<String, Long> cartCounts = new HashMap<>();

        for (Object[] row : results) {
            String category = (String) row[0];
            Long count = (Long) row[1];
            cartCounts.put(category, count);
        }

        return ResponseEntity.ok(cartCounts);
    }

    @GetMapping("order-count")
    public ResponseEntity<Map<String, Long>> getOrderCount() {
        List<Object[]> results = graphProductService.getProductOrderCounts();
        Map<String, Long> orderCounts = new HashMap<>();

        for (Object[] row : results) {
            String category = (String) row[0];
            Long count = (Long) row[1];
            orderCounts.put(category, count);
        }

        return ResponseEntity.ok(orderCounts);
    }
}