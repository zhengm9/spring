package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zhengming on 17/12/12.
 */
public class JsonParse {

    @Test
    public void test()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String var = "{\"effect_end_time\":\"2019-01-06 21:00:00\",\"effect_start_time\":\"2018-01-06 21:00:00\"}";
        String var2 = "{\"json_data\":\"{\\\"airNumber\\\":\\\"CA1329\\\",\\\"isRequireInvoice\\\":\\\"false\\\",\\\"airOrderEnd\\\":\\\"广州\\\",\\\"airTakeOff\\\":\\\"2018-01-06 21:00:00\\\",\\\"airOrderId\\\":\\\"481296698088\\\",\\\"sharedCommission\\\":false,\\\"ticketNo\\\":\\\"999-5998208633\\\",\\\"flightNo\\\":\\\"CA1329\\\",\\\"airComName\\\":\\\"中国国航\\\",\\\"insuredCopies\\\":1,\\\"airOrderStart\\\":\\\"北京\\\",\\\"bizOrderId\\\":\\\"106910716298818830\\\",\\\"airLanding\\\":\\\"2018-01-07 00:20:00\\\"}\"}";
        try {
            Map<String, String> mapsData = objectMapper.readValue(var2, Map.class);
            Map<String, String> mapsDetail = objectMapper.readValue(mapsData.get("json_data"), Map.class);
            Map<String, String> mapsEffectData = objectMapper.readValue(var, Map.class);

            System.out.println(mapsDetail.get("airNumber"));
            System.out.println(mapsDetail.get("airTakeOff"));
            System.out.println(mapsEffectData.get("effect_start_time"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
