package com.example.biblioteca.service;

import com.example.biblioteca.model.Lectura;
import com.example.biblioteca.repository.LecturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servei per gestionar les operacions CRUD de Lectura.
 */
@Service
public class LecturaService {

    private final LecturaRepository lecturaRepository;

    public LecturaService(LecturaRepository lecturaRepository) {
        this.lecturaRepository = lecturaRepository;
    }

    /** Retorna totes les lectures de la base de dades. */
    public List<Lectura> llistarTotes() {
        return lecturaRepository.findAll();
    }

    /** Busca una lectura per id. */
    public Optional<Lectura> buscarPerId(Long id) {
        return lecturaRepository.findById(id);
    }

    /** Desa una lectura nova o actualitza una d'existent. */
    public Lectura guardar(Lectura lectura) {
        return lecturaRepository.save(lectura);
    }

    /** Elimina la lectura amb l'id indicat. */
    public void eliminar(Long id) {
        lecturaRepository.deleteById(id);
    }

    /** Retorna el nombre total de lectures. */
    public long comptar() {
        return lecturaRepository.count();
    }

    /** Compta les lectures amb un estat concret (per a les estadístiques de la home). */
    public long comptarPerEstat(com.example.biblioteca.model.EstatLectura estat) {
        return lecturaRepository.countByEstat(estat);
    }

    /** Retorna totes les lectures associades a un llibre concret. */
    public java.util.List<Lectura> llistarPerLlibre(Long llibreId) {
        return lecturaRepository.findByLlibreId(llibreId);
    }
}