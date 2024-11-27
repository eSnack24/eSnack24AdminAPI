package org.esnack24api.esnack24adminapi.admintests;

import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.admin.repository.AdminRepository;
import org.esnack24api.esnack24adminapi.admin.service.AdminService;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminTests {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    @Transactional
    public void adminListTest() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        pageRequestDTO.setPage(1);
        pageRequestDTO.setSize(10);

        adminRepository.adminList(pageRequestDTO);
    }

    @Test
    @Transactional
    public void adminRoleListTest() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        pageRequestDTO.setPage(1);
        pageRequestDTO.setSize(10);

        adminRepository.adminListByRole("ALL", pageRequestDTO);
    }

    @Test
    @Transactional
    public void adminGetOne() {

        adminRepository.getAdmin(2L);
    }

    @Test
    @Transactional
    public void adminWorkListTest() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        pageRequestDTO.setPage(1);
        pageRequestDTO.setSize(10);

//        adminRepository.adminWorkList(pageRequestDTO);
    }
}
