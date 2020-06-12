package com.sgzs.febs.server.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/5/29 17:10
 */
@RestController
public class TestController {
    @GetMapping("info")
    public String test(){
        return "febs-server-system";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }


    @GetMapping("hello")
    public String hello(String name) {
        return "hello" + name;
    }
}
