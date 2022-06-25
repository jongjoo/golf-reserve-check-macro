package com.example.macro.golf.controller;

import com.example.macro.golf.service.PlazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plaza")
public class PlazaController {

    @Autowired
    PlazaService plazaService;


    @GetMapping("/timer")
    public void getTimer() {
        plazaService.call();
    }
}
