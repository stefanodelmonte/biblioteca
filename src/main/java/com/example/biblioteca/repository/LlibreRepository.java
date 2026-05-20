package com.example.biblioteca.repository;

import com.example.biblioteca.model.Llibre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LlibreRepository extends JpaRepository<Llibre, Long> {

    /**
     * Cerca llibres on el títol o l'autor continguin el text indicat
     * (insensible a majúscules). Usada pel filtre de la llista.
     */
    List<Llibre> findByTitolContainingIgnoreCaseOrAutorContainingIgnoreCase(
            String titol, String autor);
}