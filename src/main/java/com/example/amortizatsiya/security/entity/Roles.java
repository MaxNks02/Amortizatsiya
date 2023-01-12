package com.example.amortizatsiya.security.entity;

import com.example.amortizatsiya.model.BaseEntity;
import com.example.amortizatsiya.security.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 50, nullable = false)
    private RoleName name;
}
