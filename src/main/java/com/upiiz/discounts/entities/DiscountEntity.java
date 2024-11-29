package com.upiiz.discounts.entities;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "descuentos")
public class DiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_descuento")
    private String discountCode;

    @Column(name = "monto_descuento")
    private Double discountAmount;

    @Column(name = "valido_hasta")
    private LocalDate validUntil;

}
