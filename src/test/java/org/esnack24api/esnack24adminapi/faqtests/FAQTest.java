package org.esnack24api.esnack24adminapi.faqtests;


import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQDetailDTO;
import org.esnack24api.esnack24adminapi.customersupport.dto.faq.FAQListDTO;
import org.esnack24api.esnack24adminapi.customersupport.repository.FAQRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // db로 테스트?
public class FAQTest {

    @Autowired
    private FAQRepository faqRepository;

    @Test
    @Transactional
    @Commit
    public void testListFAQ() {

        List<FAQListDTO> List =  faqRepository.listFAQ();

        log.info(List);


    }

    @Test
    @Transactional
    @Commit
    public void testDetailFAQ() {

        FAQDetailDTO List = faqRepository.detailFAQ(Long.valueOf(2));

        log.info(List);


    }


}
