package com.example.blogapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/oke")
    public String home(){
        return "form";
    }
}
