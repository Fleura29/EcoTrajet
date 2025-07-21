package com.mif10_g14_2024.mif10_g14.enums;

public enum TransportType {
    CAR,
    PEDESTRIAN,
    BICYCLE;

    @Override
    public String toString() {
        return switch (this) {
            case CAR -> "car";
            case PEDESTRIAN -> "pedestrian";
            case BICYCLE -> "bicycle";
        };
    }
}
