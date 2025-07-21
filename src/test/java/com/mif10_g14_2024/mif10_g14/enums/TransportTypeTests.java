package com.mif10_g14_2024.mif10_g14.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportTypeTests {

    @Test
    public void testToString() {
        // Given
        TransportType t1 = TransportType.PEDESTRIAN;
        TransportType t2 = TransportType.BICYCLE;
        TransportType t3 = TransportType.CAR;

        // When
        String s1 = t1.toString();
        String s2 = t2.toString();
        String s3 = t3.toString();

        // Then
        assertEquals("pedestrian", s1);
        assertEquals("bicycle", s2);
        assertEquals("car", s3);
    }
}
