package com.disquesea.disqueseaapi.configuration.liquibase;


import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {
    private final DataSource dataSource;

    public LiquibaseConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SpringLiquibase springLiquibase() {

        SpringLiquibase bean = new SpringLiquibase();
        bean.setChangeLog("classpath:liquibase/changelog.sql");
        bean.setDataSource(dataSource);
        return bean;
    }

}
