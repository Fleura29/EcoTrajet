package com.mif10_g14_2024.mif10_g14.core.weighting;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.WeightingFactory;

public class CustomHopper extends GraphHopper {
    @Override
    protected WeightingFactory createWeightingFactory() {
        return new CO2WeightingFactory(getEncodingManager());
    }
}
