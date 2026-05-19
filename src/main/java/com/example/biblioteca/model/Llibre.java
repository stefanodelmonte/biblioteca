package com.example.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "llibre")
public class Llibre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank valida que el camp no sigui ni null ni buit (requereix spring-boot-starter-validation)
    @NotBlank(message = "El títol és obligatori")
    @Column(nullable = false)
    private String titol;

    @NotBlank(message = "L'autor és obligatori")
    @Column(nullable = false)
    private String autor;

    private String isbn;

    private Integer pagines;

    private String idioma;

    private Integer anyPublicacio;

    @OneToMany(mappedBy = "llibre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lectura> lectures = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "llibre_categoria", joinColumns = @JoinColumn(name = "llibre_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<Categoria> categories = new HashSet<>();

    public Llibre() {
    }

    public Llibre(String titol, String autor) {
        this.titol = titol;
        this.autor = autor;
    }

    // Getters i setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPagines() {
        return pagines;
    }

    public void setPagines(Integer pagines) {
        this.pagines = pagines;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getAnyPublicacio() {
        return anyPublicacio;
    }

    public void setAnyPublicacio(Integer anyPublicacio) {
        this.anyPublicacio = anyPublicacio;
    }

    public List<Lectura> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lectura> lectures) {
        this.lectures = lectures;
    }

    public Set<Categoria> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categoria> categories) {
        this.categories = categories;
    }
}