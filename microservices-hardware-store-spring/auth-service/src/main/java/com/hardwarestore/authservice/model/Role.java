package com.hardwarestore.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Entity
@Table(name = "roles")
@NoArgsConstructor
@Setter
public class Role extends IdBasedEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private ERole name;


}
