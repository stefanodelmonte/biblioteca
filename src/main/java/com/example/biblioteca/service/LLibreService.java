package com.example.biblioteca.service;

import com.example.biblioteca.model.Llibre;
import com.example.biblioteca.repository.LlibreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servei per gestionar les operacions CRUD de Llibre.
 * S'injecta als controllers que necessitin accedir a la base de dades de llibres.
 */
@Service
public class LlibreService {

    private final LlibreRepository llibreRepository;

    public LlibreService(LlibreRepository llibreRepository) {
        this.llibreRepository = llibreRepository;
    }

    /** Retorna tots els llibres de la base de dades. */
    public List<Llibre> llistarTots() {
        return llibreRepository.findAll();
    }

    /** Busca un llibre per id. Retorna Optional buit si no existeix. */
    public Optional<Llibre> buscarPerId(Long id) {
        return llibreRepository.findById(id);
    }

    /** Desa un llibre nou o actualitza un d'existent (si te id). */
    public Llibre guardar(Llibre llibre) {
        return llibreRepository.save(llibre);
    }

    /** Elimina el llibre amb l'id indicat. */
    public void eliminar(Long id) {
        llibreRepository.deleteById(id);
    }
}