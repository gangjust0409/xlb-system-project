package org.xlb.xlbspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestController {

    @GetMapping("/test/{name}")
    public String test(@PathVariable String name){
        String id = UUID.randomUUID().toString().replace("-", "");
        name = String.format("\"%s\"", name);
        return """
                {
                    "id": """+id+"""
                    ,"name":"""+name+"""
                    ,"age": 18
                }
                """;
    }

}
