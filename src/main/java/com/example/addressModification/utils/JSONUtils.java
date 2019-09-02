package com.example.addressModification.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public final class JSONUtils {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    public static <T> T convertJsonFileToPojo(String targetResponceFile, Class<T> type) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        // to ignore every JSON property , you don't want defined in your POJO whole
        // mapping, activanle par annotation sur l'Entité (T) ,
        // (@JsonIgnoreProperties(ignoreUnknown = true))
        T ret = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // to read a list with a single element as a JsonArray rather than a JsonNode or
            // vice versa.
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            // to read a list with a single element as a JsonArray rather than a JsonNode or
            // vice versa.
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            //disable SerializationFeature.FAIL_ON_EMPTY_BEANS
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            ret = mapper.readValue(new File(targetResponceFile), type);
            if (ret == null) {
                LoggerFactory.getLogger(JSONUtils.class).info("RespFromFile Néant ");
            }
        } catch (IOException e) {
            LoggerFactory.getLogger(JSONUtils.class).error("erreur déséraialisation File Json To Pojo  " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            LoggerFactory.getLogger(JSONUtils.class).error("Erreur dans la méthode traceListEntities ", e);
            e.printStackTrace();
        }
        return ret;
    }

    public static <T> boolean writeJsonFileFromPojo(String targetResponceFile, T value) {

        try {

            // Create ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            // Convert object to JSON string
            String json = mapper.writeValueAsString(value);
            System.out.println(json);

            // Save JSON string to file
            File fileOutput = new File(targetResponceFile);
            // delete previous content
            if (fileOutput.exists()) {
                fileOutput.delete();
                try {
                    fileOutput.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mapper.writeValue(fileOutput, value);
        } catch (IOException e) {
            LoggerFactory.getLogger(JSONUtils.class).info(" Exception : RespFromFile Néant ");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String convertPojoToJsonString(Object obj) {
        String strObj = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            //Ignore Unknown Properties
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //disable SerializationFeature.FAIL_ON_EMPTY_BEANS
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            strObj = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LoggerFactory.getLogger(JSONUtils.class).error("erreur  séraialisation Pojo to string Json " + e.getMessage());
        } catch (Exception e) {
            LoggerFactory.getLogger(JSONUtils.class).error("Erreur dans la méthode traceListEntities ", e);
        }
        return strObj;
    }

    public static String convertCompositePOJOToJsonString(Object obj) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(obj);
        return jsonStr;
    }

    public static <T> T convertCompositeJsonStringToPOJO(String jsonStr, Class<T> typePojo) {
        Gson gson = new Gson();
        T jsonObj = gson.fromJson(jsonStr,typePojo);
        return jsonObj;
    }

    public static <T> List<T> convertFromJsonFileAsListOfObject(String targetResponceFile, Class<T> pClass) throws Exception {
        List<T> ret = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(new File(targetResponceFile), new TypeReference<List<T>>() {
        });
        if (ret == null) {
            LoggerFactory.getLogger(JSONUtils.class).info("Null Exception : RespFromFile Néant ");
        }
        return ret;
    }


}
