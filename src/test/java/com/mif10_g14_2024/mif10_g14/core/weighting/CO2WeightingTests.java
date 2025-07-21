package com.mif10_g14_2024.mif10_g14.core.weighting;

import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.util.EdgeIteratorState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CO2WeightingTests {
    @Mock
    static DecimalEncodedValue speedEnc;

    static CO2Weighting weighting;

    @BeforeAll
    static void setUp() {
        // Given
        weighting = new CO2Weighting(0.1, 0.2, 0.3, speedEnc);
    }

    @Test
    public void testCaclMinWeightPerDistance() {
        // When
        double minWeight = weighting.calcMinWeightPerDistance();

        // Then
        assertEquals(0, minWeight);
    }

    @Test
    public void testCalcEdgeWeight() {
        // Given
        EdgeIteratorState edgeIteratorState = mock(EdgeIteratorState.class);
        when(edgeIteratorState.getDistance()).thenReturn(10000.0);
        when(edgeIteratorState.get(speedEnc)).thenReturn(100.0);

        // When
        double weight1 = weighting.calcEdgeWeight(edgeIteratorState, true);
        double weight2 = weighting.calcEdgeWeight(edgeIteratorState, false);

        // Then
        // Expected: (0.1 + 0.2 * 100 + 0.3 * 100²) * 10 = 30201
        assertEquals(30201, weight1);
        assertEquals(30201, weight2);
    }

    @Test
    public void testcalcEdgeMillis() {
        // Given
        EdgeIteratorState edgeIteratorState = mock(EdgeIteratorState.class);
        when(edgeIteratorState.getDistance()).thenReturn(100.0);
        when(edgeIteratorState.get(speedEnc)).thenReturn(10.0);

        // When
        long millis1 = weighting.calcEdgeMillis(edgeIteratorState, true);
        long millis2 = weighting.calcEdgeMillis(edgeIteratorState, false);

        // Then
        // Expected: (0.1 / 10) * 3 600 000 = 36 000
        assertEquals(36_000, millis1);
        assertEquals(36_000, millis2);
    }

    @Test
    public void testCalcTurnWeight() {
        // When
        double turnWeight1 = weighting.calcTurnWeight(1, 2, 3);
        double turnWeight2 = weighting.calcTurnWeight(0, 0, 0);

        // Then
        assertEquals(0, turnWeight1);
        assertEquals(0, turnWeight2);
    }

    @Test
    public void testCalcTurnMillis() {
        // When
        double turnMillis1 = weighting.calcTurnMillis(1, 2, 3);
        double turnMillis2 = weighting.calcTurnMillis(0, 0, 0);

        // Then
        assertEquals(0, turnMillis1);
        assertEquals(0, turnMillis2);
    }

    @Test
    public void testHasTurnCosts() {
        // When
        boolean has = weighting.hasTurnCosts();

        // Then
        assertFalse(has);
    }

    @Test
    public void testGetName() {
        // When
        String name = weighting.getName();

        // Then
        assertEquals("CO2Weighting", name);
    }
}
