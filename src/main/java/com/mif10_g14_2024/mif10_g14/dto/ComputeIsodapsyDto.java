package com.mif10_g14_2024.mif10_g14.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComputeIsodapsyDto {
    String sourceText;
    double sourceLat;
    double sourceLon;
    String vehicleType;
    double budget;
    Long userId;
}
