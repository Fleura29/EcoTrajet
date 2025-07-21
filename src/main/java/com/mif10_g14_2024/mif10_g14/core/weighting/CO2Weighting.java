package com.mif10_g14_2024.mif10_g14.core.weighting;

import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.util.EdgeIteratorState;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CO2 emission is calculated using 3 constants a, b, and c (based on the vehicle).
 * CO2 (kg/km) = a + b * v + c * v²
 */
@Data
@AllArgsConstructor
public class CO2Weighting implements Weighting {
    double coefA;
    double coefB;
    double coefC;
    DecimalEncodedValue speedEnc;

    @Override
    public double calcMinWeightPerDistance() {
        return 0;
    }

    /**
     * Compute CO2 emission on this edge.
     * The weight is CO2 emissions / km (in kg).
     */
    @Override
    public double calcEdgeWeight(EdgeIteratorState edgeIteratorState, boolean b) {
        double length = edgeIteratorState.getDistance() / 1000.0; // in kilometers
        double speed = edgeIteratorState.get(speedEnc);           // Should be in km/h

        // Apply formula (emissionRate in kg/km)
        double emissionRate = this.getCoefA() + this.getCoefB() * speed + this.getCoefC() * speed * speed;

        // Result in kg
        return emissionRate * length;
    }

    @Override
    public long calcEdgeMillis(EdgeIteratorState edgeIteratorState, boolean b) {
        double length = edgeIteratorState.getDistance() / 1000.0; // in kilometers
        double speed = edgeIteratorState.get(speedEnc);           // Should be in km/h

        return (long) ((length / speed) * 3_600_000);
    }

    @Override
    public double calcTurnWeight(int i, int i1, int i2) {
        return 0;
    }

    @Override
    public long calcTurnMillis(int i, int i1, int i2) {
        return 0;
    }

    @Override
    public boolean hasTurnCosts() {
        return false;
    }

    @Override
    public String getName() {
        return "CO2Weighting";
    }
}
