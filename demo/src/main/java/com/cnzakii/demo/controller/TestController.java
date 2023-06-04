package com.cnzakii.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Zaki
 * @since 2023-06-04
 **/
@RestController

public class TestController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}
