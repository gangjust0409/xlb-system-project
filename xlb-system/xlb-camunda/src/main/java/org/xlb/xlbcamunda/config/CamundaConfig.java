package org.xlb.xlbcamunda.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class CamundaConfig {

    @Bean
    public PlatformTransactionManager platformTransactionManager(@Qualifier("dataSource")DataSource dataSource){
        return new JdbcTransactionManager(dataSource);
    }

}
