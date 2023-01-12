package com.example.amortizatsiya.model.lov;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "purpose_type")
public class PurposeType extends LOVEntity{
    @Column(nullable = false, length = 200, unique = true)
    @NotBlank(message = "Name cannot be null")
    private String name;

    @Column(name = "code", unique = true)
    String code;
}
