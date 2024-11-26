package org.esnack24api.esnack24adminapi.producttests;

import lombok.extern.log4j.Log4j2;
import org.esnack24api.esnack24adminapi.common.page.PageRequest;
import org.esnack24api.esnack24adminapi.common.page.PageResponse;
import org.esnack24api.esnack24adminapi.product.dto.ProductListDTO;
import org.esnack24api.esnack24adminapi.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    // product 조회 테스트
    @Test
    @Transactional
    @Commit
    public void testProductList() {

        PageRequest pageRequest = new PageRequest();

        PageResponse<ProductListDTO> productList = productRepository.listProductAll(pageRequest);

        log.info(productList);
    }

}
