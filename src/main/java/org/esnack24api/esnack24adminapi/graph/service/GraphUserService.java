package org.esnack24api.esnack24adminapi.graph.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.graph.repository.user.GraphUserAllergyRepository;
import org.esnack24api.esnack24adminapi.graph.repository.user.GraphUserBirthRepository;
import org.esnack24api.esnack24adminapi.graph.repository.user.GraphUserCountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class GraphUserService {

    private final GraphUserCountryRepository graphUserCountryRepository;

    private final GraphUserBirthRepository graphUserBirthRepository;

    private final GraphUserAllergyRepository graphUserAllergyRepository;

    public List<Object[]> getCountryCounts() {
        return graphUserCountryRepository.countByCountry();
    }

    public List<Object[]> getBirthCounts() {
        return graphUserBirthRepository.countUserBirth();
    }

    public List<Object[]> getAllergyCounts() {
        return graphUserAllergyRepository.countUserAllergy();
    }




}
