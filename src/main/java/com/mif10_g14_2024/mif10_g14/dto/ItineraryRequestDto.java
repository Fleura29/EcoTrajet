package com.mif10_g14_2024.mif10_g14.dto;

import com.graphhopper.util.shapes.GHPoint;
import com.mif10_g14_2024.mif10_g14.entity.Transport;
import com.mif10_g14_2024.mif10_g14.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryRequestDto {
    String sourceText;
    String targetText;

    GHPoint source;
    GHPoint target;

    Transport transport;

    User user;
}

