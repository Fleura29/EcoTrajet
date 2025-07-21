package com.mif10_g14_2024.mif10_g14.entity;

import com.mif10_g14_2024.mif10_g14.core.PointList;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Nullable
    private User user;

    private Date date;

    private String transportId;

    private String source;
    private String target;

    private double distance;      // In kilometers
    private double emissions;     // Estimated CO2 emissions in kg
    private double time;          // Time in seconds
    private double ascend;        // Elevation in meters (= how much you have to go up)

    @Type(JsonType.class)
    @Column(columnDefinition = "JSON")
    private PointList points;

    // Constructor excluding the id field
    public Itinerary(@Nullable User user, Date date, String transportId, String source, String target,
                     double distance, double emissions, double time, double ascend, PointList points) {
        this.user = user;
        this.date = date;
        this.transportId = transportId;
        this.source = source;
        this.target = target;
        this.distance = distance;
        this.emissions = emissions;
        this.time = time;
        this.ascend = ascend;
        this.points = points;
    }
}
