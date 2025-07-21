package com.mif10_g14_2024.mif10_g14.controller;

import com.mif10_g14_2024.mif10_g14.entity.Itinerary;
import com.mif10_g14_2024.mif10_g14.service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
public class ItineraryController {
    @Autowired
    ItineraryService itineraryService;
    
    @QueryMapping
    public Itinerary getItinerary(@Argument Long id) {
        return itineraryService.getItineraryById(id)
                .orElseThrow(() -> new RuntimeException("Historique not found with id: " + id));
    }

    @MutationMapping
    public Itinerary saveItinerary(@Argument Long id) {
        // Pour l'instant, on ne change rien.
        // Les itinéraires sont sauvegardé en BD à leur génération.
        // On renvoie donc l'itinéraire
        return itineraryService.getItineraryById(id)
                .orElseThrow(() -> new RuntimeException("Historique not found with id: " + id));
    }

    /*@QueryMapping
    public List<TrajetHistorique> getHistoriquesByUserId(@Argument Long userId) {
        return trajetHistoriqueService.getHistoriquesByUserId(userId);
    }*/
}
