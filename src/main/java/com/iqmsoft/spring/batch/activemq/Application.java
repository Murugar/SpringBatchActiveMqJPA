
 
package com.iqmsoft.spring.batch.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.iqmsoft.spring.batch.activemq.application.configuration.AppConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;

@Configuration
@EnableAutoConfiguration
@Import(AppConfiguration.class)
public class Application {
    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }
}