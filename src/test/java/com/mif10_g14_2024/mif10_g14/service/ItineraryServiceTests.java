package com.mif10_g14_2024.mif10_g14.service;

import com.mif10_g14_2024.mif10_g14.dao.ItineraryRepository;
import com.mif10_g14_2024.mif10_g14.entity.Itinerary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {ItineraryService.class})
public class ItineraryServiceTests {
    @Autowired
    ItineraryService itineraryService;

    @MockitoBean
    ItineraryRepository itineraryRepository;

    @BeforeEach
    void setUp() {
        Itinerary i1 = mock(Itinerary.class);
        Mockito.when(i1.getId()).thenReturn(10L);

        Mockito.when(itineraryRepository.findById(1L)).thenReturn(
                Optional.of(i1)
        );
        Mockito.when(itineraryRepository.findById(2L)).thenReturn(Optional.empty());
    }

    @Test
    public void testGetItineraryById() {
        // When
        Optional<Itinerary> i1 = itineraryService.getItineraryById(1L);
        Optional<Itinerary> i2 = itineraryService.getItineraryById(2L);

        // Then
        assertTrue(i1.isPresent());
        assertEquals(10L, i1.get().getId());
        assertTrue(i2.isEmpty());
    }
}
