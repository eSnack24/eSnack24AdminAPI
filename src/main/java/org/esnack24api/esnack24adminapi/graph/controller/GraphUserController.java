package org.esnack24api.esnack24adminapi.graph.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.graph.service.GraphUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("admin/api/v1/graph/user")
@RequiredArgsConstructor
@Log4j2
public class GraphUserController {

    private final GraphUserService graphUserService;

    @GetMapping("country")
    public ResponseEntity<Map<String, Long>> getCountryCounts() {
        List<Object[]> results = graphUserService.getCountryCounts();
        Map<String, Long> countryCounts = new HashMap<>();

        for (Object[] row : results) {
            String country = (String) row[0];
            Long count = (Long) row[1];
            countryCounts.put(country, count);
        }

        return ResponseEntity.ok(countryCounts);
    }

    @GetMapping("age")
    public ResponseEntity<Map<String, Long>> getKoreanAgeCounts() {
        List<Object[]> results = graphUserService.getBirthCounts();
        Map<String, Long> ageCounts = new HashMap<>();

        for (Object[] row : results) {
            Timestamp birthTimestamp = (Timestamp) row[0];

            if (birthTimestamp == null) {
                // null인 경우 처리
                log.warn("birthTimestamp is null for row: {}", row);
                continue; // 생략하고 다음 row로 이동
            }

            try {
                LocalDate birthDate = birthTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate today = LocalDate.now();
                int age = today.getYear() - birthDate.getYear() + 1;

                // 생일이 아직 안 지난 경우 나이를 1 줄임
                if (today.isBefore(birthDate.withYear(today.getYear()))) {
                    age--;
                }

                // 나이를 10대, 20대 등으로 그룹화
                int ageGroupStart = (age / 10) * 10;
                String ageGroup = ageGroupStart + "대";

                Long count = (Long) row[1];
                ageCounts.put(ageGroup, ageCounts.getOrDefault(ageGroup, 0L) + count);
            } catch (Exception e) {
                log.error("Error processing row: {}", row, e);
            }
        }

        return ResponseEntity.ok(ageCounts);
    }



    @GetMapping("allergy")
    public ResponseEntity<Map<String, Long>> getAllergyCounts() {
        List<Object[]> results = graphUserService.getAllergyCounts();
        Map<String, Long> allergyCounts = new HashMap<>();

        for (Object[] row : results) {
            String allergy = (String) row[0];
            Long count = (Long) row[1];
            allergyCounts.put(allergy, count);
        }
        return ResponseEntity.ok(allergyCounts);
    }

}
