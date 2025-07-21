package com.mif10_g14_2024.mif10_g14.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComputeItineraryDto {
    String sourceText;
    String targetText;
    double sourceLat;
    double sourceLon;
    double targetLat;
    double targetLon;
    String vehicleType;
    Long userID;
}
