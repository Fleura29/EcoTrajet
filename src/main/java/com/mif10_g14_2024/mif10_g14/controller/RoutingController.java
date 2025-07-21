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
import com.mif10_g14_2024.mif10_g14.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class RoutingController {

    @Autowired
    RoutingService routingService;

    @Autowired
    TransportRepository transportRepository;

    @Autowired
    UserRepository userRepository;

    @QueryMapping
    public Itinerary computeItinerary(@Argument ComputeItineraryDto input) {

        Optional<Transport> transport = transportRepository.findById(input.getVehicleType());

        if (transport.isEmpty()) {
            throw new RuntimeException("Type de véhicule inconnu: " + input.getVehicleType());
        }

        GHPoint source = new GHPoint(input.getSourceLat(), input.getSourceLon());
        GHPoint target = new GHPoint(input.getTargetLat(), input.getTargetLon());

        User user = null;
        if (input.getUserID() != null) {
            Optional<User> potentialUser = userRepository.findById(input.getUserID());
            if (potentialUser.isEmpty()) {
                throw new RuntimeException("Utilisateur inconnu: " + input.getUserID());
            }
            user = potentialUser.get();
        }

        ItineraryRequestDto request = new ItineraryRequestDto(
                input.getSourceText(),
                input.getTargetText(),
                source,
                target,
                transport.get(),
                user
        );

        return routingService.getItinerary(request);
    }

    @QueryMapping
    public void computeIsodapsy(@Argument ComputeIsodapsyDto input) {
        throw new RuntimeException("Not Implemented");
    }
}
