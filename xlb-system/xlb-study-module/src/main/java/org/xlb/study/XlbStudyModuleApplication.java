package org.xlb.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "org.xlb")
@SpringBootApplication
public class XlbStudyModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(XlbStudyModuleApplication.class, args);
	}

}
