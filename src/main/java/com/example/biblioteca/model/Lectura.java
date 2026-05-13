package com.example.biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lectura")
public class Lectura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInici;

    private LocalDate dataFi;

    private Integer valoracio;

    @Column(columnDefinition = "TEXT")
    private String ressenya;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstatLectura estat;

    @ManyToOne
    @JoinColumn(name = "llibre_id", nullable = false)
    private Llibre llibre;

    @ManyToMany
    @JoinTable(
        name = "lectura_etiqueta",
        joinColumns = @JoinColumn(name = "lectura_id"),
        inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private Set<Etiqueta> etiquetes = new HashSet<>();

    public Lectura() {}

    // Getters i setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataInici() { return dataInici; }
    public void setDataInici(LocalDate dataInici) { this.dataInici = dataInici; }

    public LocalDate getDataFi() { return dataFi; }
    public void setDataFi(LocalDate dataFi) { this.dataFi = dataFi; }

    public Integer getValoracio() { return valoracio; }
    public void setValoracio(Integer valoracio) { this.valoracio = valoracio; }

    public String getRessenya() { return ressenya; }
    public void setRessenya(String ressenya) { this.ressenya = ressenya; }

    public EstatLectura getEstat() { return estat; }
    public void setEstat(EstatLectura estat) { this.estat = estat; }

    public Llibre getLlibre() { return llibre; }
    public void setLlibre(Llibre llibre) { this.llibre = llibre; }

    public Set<Etiqueta> getEtiquetes() { return etiquetes; }
    public void setEtiquetes(Set<Etiqueta> etiquetes) { this.etiquetes = etiquetes; }
}