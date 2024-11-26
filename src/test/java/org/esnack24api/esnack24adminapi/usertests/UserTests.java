package org.esnack24api.esnack24adminapi.usertests;


import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.user.repository.UserRepository;
import org.esnack24api.esnack24adminapi.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void listTest() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10);

        userRepository.getUserList(pageRequestDTO);

    }

    @Test
    @Transactional
    public void detailTest() {

        userService.userDetail(2L);

    }


}
