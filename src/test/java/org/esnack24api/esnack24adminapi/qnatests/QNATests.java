package org.esnack24api.esnack24adminapi.qnatests;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.customersupport.repository.QNARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // db로 테스트?
public class QNATests {

    @Autowired
    private QNARepository qnaRepository;

    @Transactional
    @Commit
    @Test
    public void QNAListTest(){

        log.info("Testing QNA List");

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        pageRequestDTO.setPage(1);
        pageRequestDTO.setSize(10);

        var result = qnaRepository.qnaList(pageRequestDTO);

        log.info("Result: {}", result);

    }
}
