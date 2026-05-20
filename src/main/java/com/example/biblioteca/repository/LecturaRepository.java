package com.example.biblioteca.repository;

import com.example.biblioteca.model.EstatLectura;
import com.example.biblioteca.model.Lectura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturaRepository extends JpaRepository<Lectura, Long> {

    /** Compta les lectures que tenen un estat concret (per a les estadístiques). */
    long countByEstat(EstatLectura estat);

    /** Retorna totes les lectures associades a un llibre concret (per al detall). */
    List<Lectura> findByLlibreId(Long llibreId);
}