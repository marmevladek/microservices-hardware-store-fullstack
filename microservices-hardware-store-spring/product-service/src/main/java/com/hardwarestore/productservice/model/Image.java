package com.hardwarestore.productservice.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    public Image(String name, String url) {
        this.name = name;
        this.url = url;
    }

}
