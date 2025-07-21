package com.mif10_g14_2024.mif10_g14.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
@JsonDeserialize
public record PointList(List<Double> lats, List<Double> lons) {
}
