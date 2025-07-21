package com.mif10_g14_2024.mif10_g14.controller;

import com.graphhopper.util.shapes.GHPoint;
import com.mif10_g14_2024.mif10_g14.dao.TransportRepository;
import com.mif10_g14_2024.mif10_g14.dao.UserRepository;
import com.mif10_g14_2024.mif10_g14.dto.ComputeIsodapsyDto;
import com.mif10_g14_2024.mif10_g14.dto.ComputeItineraryDto;
import com.mif10_g14_2024.mif10_g14.dto.ItineraryRequestDto;
import com.mif10_g14_2024.mif10_g14.entity.Itinerary;
import com.mif10_g14_2024.mif10_g14.entity.Transport;
import com.mif10_g14_2024.mif10_g14.entity.User;
import com.mif10_g14_2024.mif10_g14.enums.TransportType;
import com.mif10_g14_2024.mif10_g14.enums.UserType;
import com.mif10_g14_2024.mif10_g14.service.RoutingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {RoutingController.class})
public class RoutingControllerTests {
    @Autowired
    RoutingController routingController;

    @MockitoBean
    RoutingService routingService;

    @MockitoBean
    TransportRepository transportRepository;

    @MockitoBean
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(userRepository.findById(1L)).thenReturn(
                Optional.of(new User("toto", "TOTO", UserType.UTILISATEUR))
        );

        Mockito.when(userRepository.findById(2L)).thenReturn(
                Optional.empty()
        );

        Mockito.when(transportRepository.findById("car")).thenReturn(
                Optional.of(new Transport("car", TransportType.CAR, 0.1, 0.2, 0.3))
        );
        Mockito.when(transportRepository.findById("unicorn")).thenReturn(
                Optional.empty()
        );
    }

    @Test
    public void testComputeItineraryCorrect() {
        // Given
        ComputeItineraryDto input = new ComputeItineraryDto(
                "Lyon",
                "Paris",
                3.0,
                3.0,
                4.0,
                4.0,
                "car",
                1L
        );

        Itinerary result = mock(Itinerary.class);
        when(result.getId()).thenReturn(12L);

        Mockito.when(routingService.getItinerary(new ItineraryRequestDto(
                "Lyon",
                "Paris",
                new GHPoint(3.0, 3.0),
                new GHPoint(4.0, 4.0),
                new Transport("car", TransportType.CAR, 0.1, 0.2, 0.3),
                new User("toto", "TOTO", UserType.UTILISATEUR)
        ))).thenReturn(result);

        // When
        Itinerary response = assertDoesNotThrow(() ->
                routingController.computeItinerary(input)
        );

        // Then
        assertEquals(12L, response.getId());
    }

    @Test
    public void testComputeItineraryNoUser() {
        // Given
        ComputeItineraryDto input = new ComputeItineraryDto(
                "Lyon",
                "Paris",
                3.0,
                3.0,
                4.0,
                4.0,
                "car",
                null
        );

        Itinerary result = mock(Itinerary.class);
        when(result.getId()).thenReturn(12L);

        Mockito.when(routingService.getItinerary(new ItineraryRequestDto(
                "Lyon",
                "Paris",
                new GHPoint(3.0, 3.0),
                new GHPoint(4.0, 4.0),
                new Transport("car", TransportType.CAR, 0.1, 0.2, 0.3),
                null
        ))).thenReturn(result);

        // When
        Itinerary response = assertDoesNotThrow(() ->
                routingController.computeItinerary(input)
        );

        // Then
        assertEquals(12L, response.getId());
    }

    @Test
    public void testComputeItineraryWrongTransport() {
        // Given
        ComputeItineraryDto input = new ComputeItineraryDto(
                "Lyon",
                "Paris",
                3.0,
                3.0,
                4.0,
                4.0,
                "unicorn",
                null
        );

        // Then
        assertThrows(RuntimeException.class, () ->
                routingController.computeItinerary(input)
        );
    }

    @Test
    public void testComputeItineraryWrongUser() {
        // Given
        ComputeItineraryDto input = new ComputeItineraryDto(
                "Lyon",
                "Paris",
                3.0,
                3.0,
                4.0,
                4.0,
                "car",
                2L
        );

        // Then
        assertThrows(RuntimeException.class, () ->
                routingController.computeItinerary(input)
        );
    }

    @Test
    public void testComputeIsodapsy() {
        assertThrows(RuntimeException.class, () -> routingController.computeIsodapsy(mock(ComputeIsodapsyDto.class)));
    }
}
