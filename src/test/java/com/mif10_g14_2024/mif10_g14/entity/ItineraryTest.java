package com.mif10_g14_2024.mif10_g14.entity;

import com.mif10_g14_2024.mif10_g14.core.PointList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ItineraryTest {
    Itinerary itinerary;

    @BeforeEach
    void setUp() {
        // Given
        User user = mock(User.class);
        Mockito.when(user.getId()).thenReturn(3L);

        Date date = mock(Date.class);
        Mockito.when(date.getTime()).thenReturn(10L);

        PointList pl = mock(PointList.class);
        Mockito.when(pl.lats()).thenReturn(List.of(3.0, 4.0, 5.0));
        Mockito.when(pl.lons()).thenReturn(List.of(3.0, 4.0, 5.0));

        itinerary = new Itinerary(
                user,
                date,
                "car",
                "Lyon",
                "Paris",
                300,
                400,
                7200,
                50,
                pl);
    }

    @Test
    public void testConstructor() {
        // Then
        assertEquals("car", itinerary.getTransportId());
        assertEquals("Lyon", itinerary.getSource());
        assertEquals("Paris", itinerary.getTarget());
        assertEquals(300, itinerary.getDistance());
        assertEquals(400, itinerary.getEmissions());
        assertEquals(7200, itinerary.getTime());
        assertEquals(50, itinerary.getAscend());

        Assertions.assertNotNull(itinerary.getUser());

        assertEquals(3L, itinerary.getUser().getId());
        assertEquals(10L, itinerary.getDate().getTime());

        assertEquals(3, itinerary.getPoints().lats().size());
        assertEquals(3, itinerary.getPoints().lons().size());
    }
}
