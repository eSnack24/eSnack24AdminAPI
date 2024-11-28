package org.esnack24api.esnack24adminapi.reviewtests;


import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.dto.PageRequestDTO;
import org.esnack24api.esnack24adminapi.review.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // db로 테스트?
public class ReviewTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    @Test
    public void ReviewListTest(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        pageRequestDTO.setPage(1);
        pageRequestDTO.setSize(10);

        var result = reviewRepository.reviewList(pageRequestDTO);

        log.info("Result: {}", result);

    }
}
