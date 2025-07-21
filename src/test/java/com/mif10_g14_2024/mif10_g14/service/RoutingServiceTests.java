package com.mif10_g14_2024.mif10_g14.service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import com.mif10_g14_2024.mif10_g14.dao.ItineraryRepository;
import com.mif10_g14_2024.mif10_g14.dto.ItineraryRequestDto;
import com.mif10_g14_2024.mif10_g14.entity.Itinerary;
import com.mif10_g14_2024.mif10_g14.entity.Transport;
import com.mif10_g14_2024.mif10_g14.enums.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {RoutingService.class})
public class RoutingServiceTests {
    @Autowired
    RoutingService routingService;

    @MockitoBean
    ItineraryRepository itineraryRepository;

    @MockitoBean
    GraphHopper hopper;

    @BeforeEach
    void setUp() {
        Mockito.when(itineraryRepository.save(any(Itinerary.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, Itinerary.class));

        // We use the first coordinate point to "tell" the mock how
        // it should behave in order to test different cases
        Mockito.when(hopper.route(any(GHRequest.class)))
                .thenAnswer((invocation) -> {
                    GHRequest request = invocation.getArgument(0, GHRequest.class);

                    // Errors by Graphhopper
                    if (Objects.equals(request.getPoints().getFirst(), new GHPoint(0.0, 0.0))) {
                        GHResponse response = mock(GHResponse.class);
                        Mockito.when(response.hasErrors()).thenReturn(true);
                        Mockito.when(response.getErrors()).thenReturn(List.of());
                        return response;
                    }

                    // Errors in the path
                    else if (Objects.equals(request.getPoints().getFirst(), new GHPoint(1.0, 1.0))) {
                        ResponsePath bestPath = mock(ResponsePath.class);
                        Mockito.when(bestPath.isImpossible()).thenReturn(true);
                        Mockito.when(bestPath.hasErrors()).thenReturn(true);

                        GHResponse response = mock(GHResponse.class);
                        Mockito.when(response.getBest()).thenReturn(bestPath);
                        return response;
                    }

                    // No errors
                    else {
                        ResponsePath bestPath = mock(ResponsePath.class);

                        PointList list = new PointList();
                        list.add(3.0, 3.0);
                        list.add(4.0, 4.0);
                        list.add(5.0, 5.0);

                        Mockito.when(bestPath.getPoints())
                                .thenReturn(list);
                        Mockito.when(bestPath.getDistance())
                                .thenReturn(3000.0);
                        Mockito.when(bestPath.getTime())
                                .thenReturn(2000L);
                        Mockito.when(bestPath.getRouteWeight())
                                .thenReturn(3.4);
                        Mockito.when(bestPath.getAscend())
                                .thenReturn(20.0);

                        GHResponse response = mock(GHResponse.class);
                        Mockito.when(response.getBest()).thenReturn(bestPath);
                        return response;
                    }
                });
    }

    @Test
    public void testGetItineraryGHError() {
        // When
        ItineraryRequestDto request = new ItineraryRequestDto(
                "Lyon",
                "Paris",
                new GHPoint(0, 0), // source at 0, 0 to trigger an error
                new GHPoint(4, 4),
                new Transport("car", TransportType.CAR, 0.1, 0.2, 0.3),
                null
        );

        // Then
        assertThrows(RuntimeException.class, () -> routingService.getItinerary(request));
    }

    @Test
    public void testGetItineraryPathError() {
        // When
        ItineraryRequestDto request = new ItineraryRequestDto(
                "Lyon",
                "Paris",
                new GHPoint(1, 1), // source at 1, 1 to trigger an error
                new GHPoint(4, 4),
                new Transport("car", TransportType.CAR, 0.1, 0.2, 0.3),
                null
        );

        // Then
        assertThrows(RuntimeException.class, () -> routingService.getItinerary(request));
    }

    @Test
    public void testGetItineraryCorrect() {
        // Given
        ItineraryRequestDto request = new ItineraryRequestDto(
                "Lyon",
                "Paris",
                new GHPoint(3, 3),
                new GHPoint(5, 5),
                new Transport("car", TransportType.CAR, 0.1, 0.2, 0.3),
                null
        );

        // When
        Itinerary it = routingService.getItinerary(request);

        // Then
        assertNull(it.getUser());
        assertEquals("car", it.getTransportId());
        assertEquals("Lyon", it.getSource());
        assertEquals("Paris", it.getTarget());
        assertEquals(3.0, it.getDistance());
        assertEquals(2L, it.getTime());
        assertEquals(3.4, it.getEmissions());
        assertEquals(20.0, it.getAscend());

        assertEquals(3, it.getPoints().lats().size());

        assertEquals(3.0, it.getPoints().lats().get(0));
        assertEquals(3.0, it.getPoints().lons().get(0));

        assertEquals(4.0, it.getPoints().lats().get(1));
        assertEquals(4.0, it.getPoints().lons().get(1));

        assertEquals(5.0, it.getPoints().lats().get(2));
        assertEquals(5.0, it.getPoints().lons().get(2));
    }

    @Test
    void testToCoreRequest() {
        // Given
        ItineraryRequestDto itineraryRequestDto1 = mock(ItineraryRequestDto.class);
        ItineraryRequestDto itineraryRequestDto2 = mock(ItineraryRequestDto.class);
        ItineraryRequestDto itineraryRequestDto3 = mock(ItineraryRequestDto.class);

        Mockito.when(itineraryRequestDto1.getSource()).thenReturn(new GHPoint());
        Mockito.when(itineraryRequestDto2.getSource()).thenReturn(new GHPoint());
        Mockito.when(itineraryRequestDto3.getSource()).thenReturn(new GHPoint());

        Mockito.when(itineraryRequestDto1.getTarget()).thenReturn(new GHPoint());
        Mockito.when(itineraryRequestDto2.getTarget()).thenReturn(new GHPoint());
        Mockito.when(itineraryRequestDto3.getTarget()).thenReturn(new GHPoint());

        Mockito.when(itineraryRequestDto1.getTransport()).thenReturn(new Transport("car", TransportType.CAR, 0, 0, 0));
        Mockito.when(itineraryRequestDto2.getTransport()).thenReturn(new Transport("bicycle", TransportType.BICYCLE, 0, 0, 0));
        Mockito.when(itineraryRequestDto3.getTransport()).thenReturn(new Transport("pedestrian", TransportType.PEDESTRIAN, 0, 0, 0));

        // When
        GHRequest gh1 = routingService.toCoreRequest(itineraryRequestDto1);
        GHRequest gh2 = routingService.toCoreRequest(itineraryRequestDto2);
        GHRequest gh3 = routingService.toCoreRequest(itineraryRequestDto3);

        // Then
        assertEquals("car", gh1.getHints().getString("transport_type", "WRONG"));
        assertEquals("bike", gh2.getHints().getString("transport_type", "WRONG"));
        assertEquals("foot", gh3.getHints().getString("transport_type", "WRONG"));
    }
}
