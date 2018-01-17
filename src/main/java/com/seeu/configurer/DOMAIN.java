package com.seeu.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by neo on 18/07/2017.
 */
@PropertySource("classpath:application.properties")
@Component
public class DOMAIN {
    @Value("${com.seeu.domain}")
    private String domain;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
