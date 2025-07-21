package com.mif10_g14_2024.mif10_g14.core.weighting;

import com.graphhopper.config.Profile;
import com.graphhopper.routing.WeightingFactory;
import com.graphhopper.routing.ev.VehicleSpeed;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.util.PMap;

public class CO2WeightingFactory implements WeightingFactory {
    private final EncodingManager encodingManager;

    public CO2WeightingFactory(EncodingManager encodingManager) {
        this.encodingManager = encodingManager;
    }

    @Override
    public Weighting createWeighting(Profile profile, PMap hints, boolean b) {
        double coefA = hints.getDouble("vehicle_param_a", 0.5);
        double coefB = hints.getDouble("vehicle_param_b", 0.12);
        double coefC = hints.getDouble("vehicle_param_c", 0.00001);
        String transportType = hints.getString("transport_type", "car");

        return new CO2Weighting(coefA, coefB, coefC, this.encodingManager.getDecimalEncodedValue(VehicleSpeed.key(transportType)));
    }
}
