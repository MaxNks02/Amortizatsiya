package com.example.amortizatsiya.model.lov;

import com.example.amortizatsiya.model.BaseEntity;
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
@Table(name = "service_type")
public class ServiceType extends LOVEntity {

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Name cannot be null")
    private String name;

    @Column(name = "code")
    String code;
}
