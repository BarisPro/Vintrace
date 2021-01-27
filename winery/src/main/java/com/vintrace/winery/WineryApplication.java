package com.vintrace.winery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.FileNotFoundException;

@SpringBootApplication
public class WineryApplication extends SpringBootServletInitializer {
    public static void main(String[] args) throws JSONException, FileNotFoundException,InternalError {
        SpringApplication.run(WineryApplication.class, args);
    }
}



