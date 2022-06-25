package com.example.macro.golf.controller;

import com.example.macro.golf.service.AsecovalleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assco")
public class AsecovalleyController {

    @Autowired
    AsecovalleyService asecovalleyService;


    @GetMapping("/timer")
    public void getTimer(){
        asecovalleyService.call();

    }
}
