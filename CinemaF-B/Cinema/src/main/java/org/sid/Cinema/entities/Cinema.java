package org.sid.Cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Cinema implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private Double altitude,latitude,longitude;
    private int nombredesalles;
    @OneToMany(mappedBy = "cinema")
    private Collection<Salle> salles ;
    @ManyToOne
    private Ville ville ;

}
