package com.mif10_g14_2024.mif10_g14.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;

public record CoreConfig(String geodataPath, String cacheFolder) {
    static public final String CONFIG_FILE_PATH = "/coreConfig.yml";

    /**
     * Charge une configuration depuis le fichier de configuration resources/routing/coreConfig.yml.
     */
    static public CoreConfig load() throws IOException {
        ObjectMapper mapper = new YAMLMapper();
        return mapper.readValue(CoreConfig.class.getResource(CONFIG_FILE_PATH), CoreConfig.class);
    }
}
