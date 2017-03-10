
 
package com.iqmsoft.spring.batch.activemq.application.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.iqmsoft.spring.batch.activemq.controller"
})
public class WebConfiguration {
   
   // @Bean
    //public ServletRegistrationBean h2servletRegistration() {
        //final ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
       // return registration;
  //  }

   
}
