package com.company.service.ms.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static <T> List<T> deserializeStringToList(String content, Class<T> clazz) throws JsonProcessingException {
        if (content.isBlank() || content.isEmpty()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> T deserializeStringToObject(String content, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(content, clazz);
    }

    public static String serializeObjectToString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
