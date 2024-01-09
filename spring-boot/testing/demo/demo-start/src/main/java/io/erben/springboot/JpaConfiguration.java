package io.erben.springboot;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("io.erben.springboot")
@EntityScan("io.erben.springboot")
@Configuration
public class JpaConfiguration {
}
