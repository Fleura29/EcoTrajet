package com.mif10_g14_2024.mif10_g14.controller;

import com.mif10_g14_2024.mif10_g14.entity.Itinerary;
import com.mif10_g14_2024.mif10_g14.service.ItineraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {ItineraryController.class})
public class ItineraryControllerTests {

    @MockitoBean
    ItineraryService itineraryService;

    @Autowired
    ItineraryController itineraryController;

    @BeforeEach
    void setUp() {
        // Given
        Itinerary itinerary = mock(Itinerary.class);
        Mockito.when(itinerary.getId()).thenReturn(42L);

        Mockito.when(itineraryService.getItineraryById(1L)).thenReturn(
                Optional.of(itinerary)
        );
    }

    @Test
    public void testGetItinerary() {
        // Then
        assertDoesNotThrow(() -> {
            Itinerary i = itineraryController.getItinerary(1L);
            assertEquals(42L, i.getId());
        });

        assertThrows(RuntimeException.class, () -> {
            Itinerary i = itineraryController.getItinerary(3L);
        });
    }

    @Test
    public void testSaveItinerary() {
        // Then
        assertDoesNotThrow(() -> {
            Itinerary i = itineraryController.saveItinerary(1L);
            assertEquals(42L, i.getId());
        });

        assertThrows(RuntimeException.class, () -> {
            Itinerary i = itineraryController.saveItinerary(3L);
        });
    }
}
