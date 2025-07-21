package com.mif10_g14_2024.mif10_g14.service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.Profile;
import com.graphhopper.util.GHUtility;
import com.mif10_g14_2024.mif10_g14.core.CoreConfig;
import com.mif10_g14_2024.mif10_g14.core.PointList;
import com.mif10_g14_2024.mif10_g14.dao.ItineraryRepository;
import com.mif10_g14_2024.mif10_g14.dto.ItineraryRequestDto;
import com.mif10_g14_2024.mif10_g14.entity.Itinerary;
import com.mif10_g14_2024.mif10_g14.entity.Transport;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Interface principale pour faire des requêtes de routage.
 */
@Service
public class RoutingService {

    @Autowired
    ItineraryRepository itineraryRepository;

    @Autowired
    GraphHopper hopper;

    CoreConfig configuration;

    public RoutingService() throws IOException {
        this.configuration = CoreConfig.load();
    }

    @PostConstruct
    public void initHopper() {
        hopper.setStoreOnFlush(true);
        hopper.setGraphHopperLocation(configuration.cacheFolder());

        hopper.setEncodedValuesString("car_access, car_average_speed, road_access," +
                "foot_access, foot_priority, foot_average_speed," +
                "hike_rating, mtb_rating," +
                "average_slope, country, road_class, bike_priority," +
                "bike_access, roundabout, bike_average_speed");

        // Create profiles
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("car").setCustomModel(GHUtility.loadCustomModelFromJar("car.json")));
        profiles.add(new Profile("pedestrian").setCustomModel(GHUtility.loadCustomModelFromJar("foot.json")));
        profiles.add(new Profile("bicycle").setCustomModel(GHUtility.loadCustomModelFromJar("bike.json")));

        hopper.setProfiles(profiles);

        hopper.load();
    }

    GHRequest toCoreRequest(ItineraryRequestDto request) {
        Transport transport = request.getTransport();

        GHRequest ghRequest = new GHRequest(request.getSource(), request.getTarget())
                .setLocale(Locale.FRANCE)
                .setProfile(transport.getType().toString());

        ghRequest.putHint("vehicle_param_a", transport.getCoeff_a());
        ghRequest.putHint("vehicle_param_b", transport.getCoeff_b());
        ghRequest.putHint("vehicle_param_c", transport.getCoeff_c());

        String transport_type = switch (transport.getType()) {
            case CAR -> "car";
            case PEDESTRIAN -> "foot";
            case BICYCLE -> "bike";
        };

        ghRequest.putHint("transport_type", transport_type);

        return ghRequest;
    }


    public Itinerary getItinerary(ItineraryRequestDto request) {
        GHRequest coreRequest = toCoreRequest(request);

        // Compute route
        GHResponse response = hopper.route(coreRequest);

        // handle errors
        if (response.hasErrors())
            throw new RuntimeException(response.getErrors().toString());

        ResponsePath bestPath = response.getBest();

        if (bestPath.isImpossible() || bestPath.hasErrors()) {
            throw new RuntimeException("Routing error");
        }

        // Generate Itinerary
        Date today = new Date();

        PointList pointList = new PointList(new ArrayList<>(), new ArrayList<>());

        bestPath.getPoints().forEach(p -> {
            pointList.lats().add(p.getLat());
            pointList.lons().add(p.getLon());
        });

        double distance = bestPath.getDistance() / 1000.0;   // In km
        double time = bestPath.getTime() / 1000.0;           // In seconds
        double emissions = bestPath.getRouteWeight();        // In kg
        double ascend = bestPath.getAscend();                // In meters

        Itinerary itinerary = new Itinerary(
                request.getUser(),
                today,
                request.getTransport().getId(),
                request.getSourceText(),
                request.getTargetText(),
                distance,
                emissions,
                time,
                ascend,
                pointList
        );

        // Pour l'instant on sauvegarde tous les itinéraires
        // générés, à terme on sauvegarde qu'au moment de les ajouter
        // en historique.
        return itineraryRepository.save(itinerary);
    }
}
