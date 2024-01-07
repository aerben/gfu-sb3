package io.erben.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("io.erben.springboot.testing")
@EntityScan("io.erben.springboot.testing")
public class BasicSpringApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BasicSpringApplication.class, args);
    }
}
