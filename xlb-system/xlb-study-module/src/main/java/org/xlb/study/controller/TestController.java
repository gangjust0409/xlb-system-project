package org.xlb.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/{id}")
    public String test(@PathVariable String id){

        return """
                {
                    "code": 200,
                    "data": {
                           "id": """+id+"""
                    },
                    "message": "查询成功！"
                }
                """;

    /*    return "{" +
                "\"code\":200," +
                "\"data\": {" +
                "\"id\":"+id +
                "}," +
                "\"message\": \"查询成功\"" +
                "}";*/
    }

}
