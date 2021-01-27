package com.vintrace.winery.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;
import com.vintrace.winery.service.WineService;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:9191")
public class WineController {
    @Autowired
    private WineService wineService;

    // breakdown by unique year
    @GetMapping("/api/breakdown/year/{IotCode}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public JSONObject breakdownByYear(@PathVariable String IotCode) throws IOException, ParseException, JSONException, InternalError {
        return wineService.yearBreakdown(wineService.readJsonFile(IotCode));
    }

    // breakdown by unique variety
    @GetMapping("/api/breakdown/variety/{IotCode}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public JSONObject breakdownByVariety(@PathVariable String IotCode) throws IOException, ParseException, JSONException, InternalError {
        return wineService.varietyBreakdown(wineService.readJsonFile(IotCode));
    }

    // breakdown by unique region
    @GetMapping("/api/breakdown/region/{IotCode}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public JSONObject breakdownByRegion(@PathVariable String IotCode) throws IOException, ParseException, JSONException, InternalError {
        return wineService.regionBreakdown(wineService.readJsonFile(IotCode));
    }

    // breakdown by unique year and variety
    @GetMapping("/api/breakdown/year-variety/{IotCode}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public JSONObject breakdownByYearVariety(@PathVariable String IotCode) throws IOException, ParseException, JSONException, InternalError {
        return wineService.yearVarietyBreakdown(wineService.readJsonFile(IotCode));
    }

    // JSON array from the 3 different sources
    @GetMapping("/api/breakdown/year/all")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public JSONArray breakdownAll() throws IOException, ParseException, InternalError {
        return wineService.readMultipleJsonFiles();
    }
}
