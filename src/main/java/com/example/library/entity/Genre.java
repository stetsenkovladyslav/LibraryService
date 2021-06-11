package com.example.library.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "genres")
@NoArgsConstructor
public class Genre {
    public Genre(Long id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "name")
    private String genreName;

}
