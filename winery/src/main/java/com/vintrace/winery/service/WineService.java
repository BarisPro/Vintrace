package com.vintrace.winery.service;

import com.vintrace.winery.entity.Grapes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static java.util.Comparator.comparingDouble;

@Service
public class WineService {

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {
            Map<Object, Boolean> seen = new ConcurrentHashMap<>();
            return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    // sorting the grapes by percentage
    public static List<Grapes> sortPercentage(List<Grapes> grapesList){
        List<Grapes> sortedList = grapesList.stream()
                .sorted(comparingDouble(Grapes::getPercentage).reversed())
                .collect(Collectors.toList());
        return sortedList;
    }

    // reading Json file
    public List<Grapes> readJsonFile(String id) throws IOException, ParseException,InternalError, FileNotFoundException {
        List<Grapes> grapesList = new ArrayList<>();

        // parsing file
        Object obj = new JSONParser().parse(new FileReader("src/main/resources/"+id+".json"));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting array
        JSONArray ja = (JSONArray) jo.get("components");

        Iterator iterator = ja.iterator();
        while (iterator.hasNext()) {
            JSONObject item = (JSONObject) iterator.next();
            double percentage = (double)item.get("percentage");
            long year = (long)item.get("year");
            String variety = (String)item.get("variety");
            String region = (String)item.get("region");
            grapesList.add(
                    Grapes.builder().percentage(percentage).
                            year(year).
                            variety(variety).
                            region(region).
                            build());
        }
        return grapesList;
    }

    // a breakdown of the TOTAL percentage for each unique year value
    public static JSONObject yearBreakdown(List<Grapes> grapesList) throws JSONException {
        JSONObject jsonOriginal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonOriginal.put("breakDownType","year");
        if(grapesList.isEmpty()){
            jsonOriginal.put("The input is empty.","");
        }
        List<Grapes> distinctYearList = sortPercentage(grapesList).stream()
                .filter(distinctByKey(p -> p.getYear()))
                .collect(Collectors.toList());
        List<Grapes> newList = new ArrayList<>();
        for (Grapes modelListdata : distinctYearList) {
            for (Grapes prevListdata : sortPercentage(grapesList)) {
                if (prevListdata.getYear() == (modelListdata.getYear())
                        && !prevListdata.getVariety().equals(modelListdata.getVariety())
                        && !prevListdata.getRegion().equals(modelListdata.getRegion())) {
                    newList.add(
                            Grapes.builder().year(modelListdata.getYear())
                                    .percentage(modelListdata.getPercentage()+prevListdata.getPercentage())
                                    .build());

                }
                newList.add(
                        Grapes.builder().year(modelListdata.getYear())
                                .percentage(modelListdata.getPercentage())
                                .build());
            }

        }
        for (Grapes grapes:sortPercentage(newList).stream()
                .filter(distinctByKey(p -> p.getYear()))
                .collect(Collectors.toList())){
            JSONObject newJsonObject = new JSONObject();
            newJsonObject.put("percentage", grapes.getPercentage());
            newJsonObject.put("key", grapes.getYear());
            jsonArray.add(newJsonObject);
        }
        jsonOriginal.put("breakdown",jsonArray);
        return jsonOriginal;
    }

    // a breakdown of the TOTAL percentage for each unique variety value
    public static JSONObject varietyBreakdown(List<Grapes> grapesList) throws JSONException {
        JSONObject jsonOriginal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonOriginal.put("breakDownType","variety");
        if(grapesList.isEmpty()){
            jsonOriginal.put("The input is empty.","");
        }
        List<Grapes> distinctVarietyList = sortPercentage(grapesList).stream()
                .filter(distinctByKey(p -> p.getVariety()))
                .collect(Collectors.toList());
        List<Grapes> newList = new ArrayList<>();
        for (Grapes modelListdata : distinctVarietyList) {
            for (Grapes prevListdata : sortPercentage(grapesList)) {
                if (prevListdata.getVariety().equals(modelListdata.getVariety())
                        && !prevListdata.getRegion().equals(modelListdata.getRegion())
                        && prevListdata.getYear() != modelListdata.getYear()
                ) {
                    newList.add(
                            Grapes.builder().variety(modelListdata.getVariety())
                                    .percentage(modelListdata.getPercentage()+prevListdata.getPercentage())
                                    .build());

                }
                newList.add(
                        Grapes.builder().variety(modelListdata.getVariety())
                                .percentage(modelListdata.getPercentage())
                                .build());
            }

        }
        for (Grapes grapes:sortPercentage(newList).stream()
                .filter(distinctByKey(p -> p.getVariety()))
                .collect(Collectors.toList())){
            JSONObject newJsonObject = new JSONObject();
            newJsonObject.put("percentage", grapes.getPercentage());
            newJsonObject.put("key", grapes.getVariety());
            jsonArray.add(newJsonObject);
        }
        jsonOriginal.put("breakdown",jsonArray);
        return jsonOriginal;
    }

    // a breakdown of the TOTAL percentage for each unique region value
    public static JSONObject regionBreakdown(List<Grapes> grapesList) throws JSONException {
        JSONObject jsonOriginal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonOriginal.put("breakDownType","region");
        if(grapesList.isEmpty()){
            jsonOriginal.put("The input is empty.","");
        }
        List<Grapes> distinctRegionList = sortPercentage(grapesList).stream()
                .filter(distinctByKey(p -> p.getRegion()))
                .collect(Collectors.toList());
        List<Grapes> newList = new ArrayList<>();
        for (Grapes modelListdata : distinctRegionList) {
            for (Grapes prevListdata : sortPercentage(grapesList)) {
                if (prevListdata.getRegion() == (modelListdata.getRegion())
                        && prevListdata.getVariety().equals(modelListdata.getVariety())
                        && prevListdata.getYear() != (modelListdata.getYear())
                ) {
                    newList.add(
                            Grapes.builder().region(modelListdata.getRegion())
                                    .percentage(modelListdata.getPercentage()+prevListdata.getPercentage())
                                    .build());

                }
                newList.add(
                        Grapes.builder().region(modelListdata.getRegion())
                                .percentage(modelListdata.getPercentage())
                                .build());
            }

        }
        for (Grapes grapes:sortPercentage(newList).stream()
                .filter(distinctByKey(p -> p.getRegion()))
                .collect(Collectors.toList())){
            JSONObject newJsonObject = new JSONObject();
            newJsonObject.put("percentage", grapes.getPercentage());
            newJsonObject.put("key", grapes.getRegion());
            jsonArray.add(newJsonObject);
        }
        jsonOriginal.put("breakdown",jsonArray);
        return jsonOriginal;
    }

    // a breakdown of the TOTAL percentage for each unique combination year and variety value
    public static JSONObject yearVarietyBreakdown(List<Grapes> grapesList) throws JSONException {
        JSONObject jsonOriginal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonOriginal.put("breakDownType","variety");
        if(grapesList.isEmpty()){
            jsonOriginal.put("The input is empty.","");
        }
        List<Grapes> distinctVarietyList = sortPercentage(grapesList).stream()
                .filter(distinctByKey(p -> p.getVariety()))
                .collect(Collectors.toList());
        List<Grapes> newList = new ArrayList<>();
        for (Grapes modelListdata : distinctVarietyList) {
            for (Grapes prevListdata : sortPercentage(grapesList)) {
                if (prevListdata.getVariety().equals(modelListdata.getVariety())
                        && !prevListdata.getRegion().equals(modelListdata.getRegion())
                        && prevListdata.getYear() == modelListdata.getYear()
                ) {
                    newList.add(
                            Grapes.builder().variety(modelListdata.getVariety())
                                    .percentage(modelListdata.getPercentage()+prevListdata.getPercentage())
                                    .build());

                }
                newList.add(
                        Grapes.builder().variety(modelListdata.getVariety())
                                .percentage(modelListdata.getPercentage())
                                .build());
            }
        }
        // common items not matched
        if(sortPercentage(newList).get(0).getPercentage()== distinctVarietyList.get(0).getPercentage()){
            newList = null;
        }
        if (newList != null){
            for (Grapes grapes:sortPercentage(newList).stream()
                    .filter(distinctByKey(p -> p.getVariety()))
                    .collect(Collectors.toList())){
                JSONObject newJsonObject = new JSONObject();
                newJsonObject.put("percentage", grapes.getPercentage());
                newJsonObject.put("key", grapes.getVariety());
                jsonArray.add(newJsonObject);
            }
            jsonOriginal.put("breakdown",jsonArray);
        }
        else{
            // no common year and variety value
            jsonOriginal.put("breakdown",jsonArray);
        }
        return jsonOriginal;
    }

    //read all the Json files to display on React
    public JSONArray readMultipleJsonFiles() throws IOException, ParseException {
        Object obj1 = new JSONParser().parse(new FileReader("src/main/resources/11YVCHAR001.json"));
        Object obj2 = new JSONParser().parse(new FileReader("src/main/resources/11YVCHAR002.json"));
        Object obj3 = new JSONParser().parse(new FileReader("src/main/resources/15MPPN002-VK.json"));

        JSONArray jsonArray = new JSONArray();

        JSONObject jo = (JSONObject) obj1;
        String lotCode = (String) jo.get("lotCode");
        double volume = (double) jo.get("volume");
        String description = (String) jo.get("description");
        String tankCode = (String) jo.get("tankCode");
        String productState = (String) jo.get("productState");
        String ownerName = (String) jo.get("ownerName");
        JSONObject newJo = new JSONObject();
        newJo.put("lotCode",lotCode);
        newJo.put("volume",volume);
        newJo.put("description",description);
        newJo.put("tankCode",tankCode);
        newJo.put("productState",productState);
        newJo.put("ownerName",ownerName);
        jsonArray.add(newJo);

        JSONObject jo2 = (JSONObject) obj2;
        String lotCode2 = (String) jo2.get("lotCode");
        double volume2 = (double) jo2.get("volume");
        String description2 = (String) jo2.get("description");
        String tankCode2 = (String) jo2.get("tankCode");
        String productState2 = (String) jo2.get("productState");
        String ownerName2 = (String) jo2.get("ownerName");
        JSONObject newJo2 = new JSONObject();
        newJo2.put("lotCode",lotCode2);
        newJo2.put("volume",volume2);
        newJo2.put("description",description2);
        newJo2.put("tankCode",tankCode2);
        newJo2.put("productState",productState2);
        newJo2.put("ownerName",ownerName2);
        jsonArray.add(newJo2);

        JSONObject jo3 = (JSONObject) obj3;
        String lotCode3 = (String) jo3.get("lotCode");
        double volume3 = (double) jo3.get("volume");
        String description3 = (String) jo3.get("description");
        String tankCode3 = (String) jo3.get("tankCode");
        String productState3 = (String) jo3.get("productState");
        String ownerName3 = (String) jo3.get("ownerName");
        JSONObject newJo3 = new JSONObject();
        newJo3.put("lotCode",lotCode3);
        newJo3.put("volume",volume3);
        newJo3.put("description",description3);
        newJo3.put("tankCode",tankCode3);
        newJo3.put("productState",productState3);
        newJo3.put("ownerName",ownerName3);
        jsonArray.add(newJo3);

        return jsonArray;
    }
}
