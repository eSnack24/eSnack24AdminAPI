package org.esnack24api.esnack24adminapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ESnack24AdminApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ESnack24AdminApiApplication.class, args);
    }

}
