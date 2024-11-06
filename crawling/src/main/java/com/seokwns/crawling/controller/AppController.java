package com.seokwns.crawling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/")
    public String index() {
        return "main";
    }

    @GetMapping("/keyword")
    public String keyword() {
        return "keyword";
    }
}
