package com.example.biblioteca.service;

import com.example.biblioteca.model.EstatLectura;
import com.example.biblioteca.model.Lectura;
import com.example.biblioteca.repository.LecturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servei per gestionar les operacions CRUD de Lectura.
 * Implementa ServeiCrud per garantir la interfície comuna (RA7).
 */
@Service
public class LecturaService implements ServeiCrud<Lectura> {

    private final LecturaRepository lecturaRepository;

    public LecturaService(LecturaRepository lecturaRepository) {
        this.lecturaRepository = lecturaRepository;
    }

    /** Retorna totes les lectures de la base de dades. */
    @Override
    public List<Lectura> llistarTots() {
        return lecturaRepository.findAll();
    }

    /** Busca una lectura per id. */
    @Override
    public Optional<Lectura> buscarPerId(Long id) {
        return lecturaRepository.findById(id);
    }

    /** Desa una lectura nova o actualitza una d'existent. */
    @Override
    public Lectura guardar(Lectura lectura) {
        return lecturaRepository.save(lectura);
    }

    /** Elimina la lectura amb l'id indicat. */
    @Override
    public void eliminar(Long id) {
        lecturaRepository.deleteById(id);
    }

    /** Retorna el nombre total de lectures. */
    public long comptar() {
        return lecturaRepository.count();
    }

    /** Compta les lectures amb un estat concret (per a les estadístiques de la home). */
    public long comptarPerEstat(EstatLectura estat) {
        return lecturaRepository.countByEstat(estat);
    }

    /** Retorna totes les lectures associades a un llibre concret. */
    public List<Lectura> llistarPerLlibre(Long llibreId) {
        return lecturaRepository.findByLlibreId(llibreId);
    }
}
