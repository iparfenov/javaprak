package com.prak.web;

import com.prak.web.components.DAOsBean;
import com.prak.web.components.SessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class Config {

    @Bean
    public SessionFactoryBean sessionFactoryBean() {
        return new SessionFactoryBean();
    }

    @Bean
    @SessionScope
    public DAOsBean daos() {
        return new DAOsBean(sessionFactoryBean());
    }

}
