package org.esnack24api.esnack24adminapi.usertests;


import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.graph.repository.user.GraphUserCountryRepository;
import org.esnack24api.esnack24adminapi.graph.service.GraphUserService;
import org.esnack24api.esnack24adminapi.user.repository.UserRepository;
import org.esnack24api.esnack24adminapi.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GraphUserService graphUserService;
    @Autowired
    private GraphUserCountryRepository graphUserCountryRepository;

    //사용자 리스트 출력 테스트
    @Test
    @Transactional
    public void listTest() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10);

        userRepository.getUserList(pageRequestDTO);

    }

    //사용자 리스트 상세보기 테스트
    @Test
    @Transactional
    public void detailTest() {

        userService.userDetail(2L);

    }

    //사용자 나라별 카운트 테스트
    @Test
    @Transactional
    public void countCountryTest() {

        graphUserService.getCountryCounts();
    }

    //사용자 나이별 카운트 테스트
    @Test
    @Transactional
    public void countBirthTest() {
        graphUserService.getBirthCounts();
    }

    //사용자 알러지별 카운트 테스트
    @Test
    @Transactional
    public void countAllergyTest() {
        graphUserService.getAllergyCounts();
    }

}
