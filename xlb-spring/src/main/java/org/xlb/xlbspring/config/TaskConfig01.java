package org.xlb.xlbspring.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskConfig01 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("TaskConfig01 --> CommandLineRunner --> run --> args " + args);
    }
}
