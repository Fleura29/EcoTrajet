package com.mif10_g14_2024.mif10_g14.core.weighting;

import com.graphhopper.config.Profile;
import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.ev.VehicleSpeed;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.PMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CO2WeightingFactoryTests {
    CO2WeightingFactory factory;

    DecimalEncodedValue value = mock(DecimalEncodedValue.class);

    @BeforeEach
    void setUp() {
        EncodingManager encodingManager = mock(EncodingManager.class);
        Mockito.when(encodingManager.getDecimalEncodedValue(VehicleSpeed.key("car")))
                .thenReturn(value);

        factory = new CO2WeightingFactory(encodingManager);
    }

    @Test
    public void testCreateWeighting() {
        // Given
        Profile profile = mock(Profile.class);

        PMap hints = new PMap();
        hints.putObject("vehicle_param_a", 0.1);
        hints.putObject("vehicle_param_b", 0.2);
        hints.putObject("vehicle_param_c", 0.3);
        hints.putObject("transport_type", "car");

        // When
        CO2Weighting weightingF = (CO2Weighting) factory.createWeighting(profile, hints, false);
        CO2Weighting weightingT = (CO2Weighting) factory.createWeighting(profile, hints, true);
        CO2Weighting weightingD = (CO2Weighting) factory.createWeighting(profile, new PMap(), false);

        // Then
        assertEquals(0.1, weightingF.getCoefA());
        assertEquals(0.2, weightingF.getCoefB());
        assertEquals(0.3, weightingF.getCoefC());

        assertEquals(0.1, weightingT.getCoefA());
        assertEquals(0.2, weightingT.getCoefB());
        assertEquals(0.3, weightingT.getCoefC());

        assertEquals(0.5, weightingD.getCoefA());
        assertEquals(0.12, weightingD.getCoefB());
        assertEquals(0.00001, weightingD.getCoefC());

        assertEquals(value, weightingF.getSpeedEnc());
        assertEquals(value, weightingT.getSpeedEnc());
        assertEquals(value, weightingD.getSpeedEnc());
    }
}
