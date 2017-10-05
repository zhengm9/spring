package com.future.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zhengming on 17/10/5.
 */
public class JsonConverter {
    public static Map convertJson2Map(String jsonTxt) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(jsonTxt, Map.class);
        return map;
    }
}
