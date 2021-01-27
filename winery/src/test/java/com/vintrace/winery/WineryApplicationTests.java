package com.vintrace.winery;

import com.vintrace.winery.entity.Grapes;
import com.vintrace.winery.service.WineService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
 Tests for the different endpoints based on 11YVCHAR001.json
 */
@SpringBootTest
class WineryApplicationTests {

    private <List> java.util.List<Grapes> mockGrapes(){
        Grapes grape1 = Grapes.builder().percentage(5.0).
                year(2011).
                variety("Pinot Noir").
                region("Mornington").
                build();

        Grapes grape2 = Grapes.builder().percentage(80.0).
                year(2011).
                variety("Chardonnay").
                region("Yarra Valley").
                build();

        Grapes grape3 = Grapes.builder().percentage(5.00).
                year(2010).
                variety("Pinot Noir").
                region("Macedon").
                build();

        Grapes grape4 = Grapes.builder().percentage(10.00).
                year(2010).
                variety("Chardonnay").
                region("Macedon").
                build();

        java.util.List<Grapes> testList = new ArrayList<>();
        testList.add(grape1);
        testList.add(grape2);
        testList.add(grape3);
        testList.add(grape4);

        return testList;
    }

    @Test
    public void testYearBreakdown() throws JSONException {
        List<Grapes> wineMock = mockGrapes();
        JSONObject output = WineService.yearBreakdown(wineMock);
        assertTrue( output.toString().equals("{\"breakdown\":[{\"percentage\":85.0,\"key\":2011},{\"percentage\":10.0,\"key\":2010}],\"breakDownType\":\"year\"}"));
    }

    @Test
    public void testVarietyBreakdown() throws JSONException {
        List<Grapes> wineMock = mockGrapes();
        JSONObject output = WineService.varietyBreakdown(wineMock);
        assertEquals(output.toString(),"{\"breakdown\":[{\"percentage\":90.0,\"key\":\"Chardonnay\"},{\"percentage\":10.0,\"key\":\"Pinot Noir\"}],\"breakDownType\":\"variety\"}");
    }

    @Test
    public void testRegionBreakdown() throws JSONException {
        List<Grapes> wineMock = mockGrapes();
        JSONObject output = WineService.regionBreakdown(wineMock);
        assertTrue( output.toString().equals("{\"breakdown\":[{\"percentage\":80.0,\"key\":\"Yarra Valley\"},{\"percentage\":10.0,\"key\":\"Macedon\"},{\"percentage\":5.0,\"key\":\"Mornington\"}],\"breakDownType\":\"region\"}"));
    }

    @Test
    public void testYearVarietyBreakdown() throws JSONException {
        List<Grapes> wineMock = mockGrapes();
        JSONObject output = WineService.yearVarietyBreakdown(wineMock);
        assertTrue( output.toString().equals("{\"breakdown\":[],\"breakDownType\":\"variety\"}"));
    }

    @Test
    void contextLoads() {
    }

}
