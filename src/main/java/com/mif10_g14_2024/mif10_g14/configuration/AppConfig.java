package com.mif10_g14_2024.mif10_g14.configuration;

import com.graphhopper.GraphHopper;
import com.mif10_g14_2024.mif10_g14.core.weighting.CustomHopper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public GraphHopper hopper() {
        return new CustomHopper();
    }
}
