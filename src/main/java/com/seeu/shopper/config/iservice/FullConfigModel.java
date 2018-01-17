package com.seeu.shopper.config.iservice;

import com.seeu.shopper.config.model.Config;
import com.seeu.shopper.config.service.ConfigService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neo on 20/09/2017.
 */
public class FullConfigModel {
    private static Map<String, String> config;

    public static Map<String, String> getConfig(ConfigService service) {
        if (config == null) {
            List<Config> configList = service.findAll();
            Map<String, String> map = new HashMap<>();
            for (Config config : configList) {
                map.put(config.getAttributeName(), config.getAttributeValue());
            }
            config = map;
        }
        return config;
    }

    public static void flushConfig() {
        config = null;
    }
}
