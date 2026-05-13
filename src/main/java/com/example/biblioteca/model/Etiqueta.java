package com.example.biblioteca.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "etiqueta")
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @ManyToMany(mappedBy = "etiquetes")
    private Set<Lectura> lectures = new HashSet<>();

    public Etiqueta() {}

    public Etiqueta(String nom) {
        this.nom = nom;
    }

    // Getters i setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Set<Lectura> getLectures() { return lectures; }
    public void setLectures(Set<Lectura> lectures) { this.lectures = lectures; }
}