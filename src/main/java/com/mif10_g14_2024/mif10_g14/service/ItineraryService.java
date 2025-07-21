package com.mif10_g14_2024.mif10_g14.service;

import com.mif10_g14_2024.mif10_g14.dao.ItineraryRepository;
import com.mif10_g14_2024.mif10_g14.entity.Itinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItineraryService {
    @Autowired
    ItineraryRepository itineraryRepository;


    /*public List<TrajetHistorique> getAllHistoriques() {
        return trajetHistoriqueRepository.getAllHistoriques();
    }*/

    public Optional<Itinerary> getItineraryById(Long id) {
        return itineraryRepository.findById(id);
    }

    /*public List<TrajetHistorique> getHistoriquesByUserId(Long userId) {
        return trajetHistoriqueRepository.getHistoriquesByUserId(userId);
    }*/

    /*public TrajetHistorique createHistorique(TrajetHistorique historique) {
        return trajetHistoriqueRepository.save(historique);
    }*/
}
