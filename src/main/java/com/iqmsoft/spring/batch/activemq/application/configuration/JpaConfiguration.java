

package com.iqmsoft.spring.batch.activemq.application.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories({
        "com.iqmsoft.spring.batch.activemq.repository"
})
@EntityScan(basePackages = {
        "com.iqmsoft.spring.batch.activemq.model"
})
public class JpaConfiguration {
}
