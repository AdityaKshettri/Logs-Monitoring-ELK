package com.aditya.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

@Slf4j
@RestController
public class LogsController {

    @GetMapping("/elk")
    public String elk() {
        String response = "Welcome to my page at " + new Date();
        log.info(response);
        return response;
    }

    @GetMapping("/exception")
    public String exception() {
        String response;
        try {
            throw new Exception("Exception has occurred at " + new Date());
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            log.error("Exception - " + stackTrace);
            response = stackTrace;
        }
        return response;
    }
}
