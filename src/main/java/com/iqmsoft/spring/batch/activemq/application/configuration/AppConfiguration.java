
package com.iqmsoft.spring.batch.activemq.application.configuration;

import org.springframework.context.annotation.Import;

@Import({
        JpaConfiguration.class,
        JmsConfiguration.class,
        BatchConfiguration.class,
        IntegrationConfiguration.class,
        WebConfiguration.class,
        SecurityConfiguration.class,
        MonitoringConfig.class,
        SwaggerConfiguration.class
})
public class AppConfiguration {
}
