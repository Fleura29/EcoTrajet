package com.mif10_g14_2024.mif10_g14.entity;

import com.mif10_g14_2024.mif10_g14.enums.TransportType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transport {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private TransportType type;

    private double coeff_a;
    private double coeff_b;
    private double coeff_c;
}
