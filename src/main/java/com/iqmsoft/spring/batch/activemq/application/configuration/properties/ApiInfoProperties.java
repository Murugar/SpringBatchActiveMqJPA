


package com.iqmsoft.spring.batch.activemq.application.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.ApiInfo;

/**
 * Swagger {@link ApiInfo} {@link ConfigurationProperties}
 */
@ConfigurationProperties(value = ApiInfoProperties.API_INFO_PREFIX)
public class ApiInfoProperties {
    public static final String API_INFO_PREFIX = "swagger.api.info";
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String contact;
    private String license;
    private String licenseUrl;
    private String version;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public String getContact() {
        return contact;
    }

    public String getLicense() {
        return license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
