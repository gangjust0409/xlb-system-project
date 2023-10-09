package org.xlb.study.config;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientConfiguration;
import io.camunda.zeebe.client.impl.ZeebeClientBuilderImpl;
import io.camunda.zeebe.client.impl.ZeebeClientImpl;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

//使用通配符
@EnableScheduling
@Deployment(resources = { "classpath*:/bpmn/**/*.bpmn" })
@Configuration
public class CamundaConfig {

    private Logger log = LoggerFactory.getLogger(CamundaConfig.class);

    @Bean("zeebeClientConfiguration")
    public ZeebeClientConfiguration zeebeClientConfiguration(){
        return new ZeebeClientBuilderImpl();
    }

//    @Bean
    public ZeebeClient zeebeClient(@Qualifier("zeebeClientConfiguration") ZeebeClientConfiguration zeebeClientConfiguration){
        return new ZeebeClientImpl(zeebeClientConfiguration);
    }

}
