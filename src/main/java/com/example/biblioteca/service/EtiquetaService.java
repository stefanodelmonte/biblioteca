package com.example.biblioteca.service;

import com.example.biblioteca.model.Etiqueta;
import com.example.biblioteca.repository.EtiquetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servei per gestionar les operacions CRUD d'Etiqueta.
 * Implementa ServeiCrud per garantir la interfície comuna (RA7).
 */
@Service
public class EtiquetaService implements ServeiCrud<Etiqueta> {

    private final EtiquetaRepository etiquetaRepository;

    public EtiquetaService(EtiquetaRepository etiquetaRepository) {
        this.etiquetaRepository = etiquetaRepository;
    }

    /** Retorna totes les etiquetes de la base de dades. */
    @Override
    public List<Etiqueta> llistarTots() {
        return etiquetaRepository.findAll();
    }

    /** Busca una etiqueta per id. */
    @Override
    public Optional<Etiqueta> buscarPerId(Long id) {
        return etiquetaRepository.findById(id);
    }

    /** Desa una etiqueta nova o actualitza una d'existent. */
    @Override
    public Etiqueta guardar(Etiqueta etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }

    /** Elimina l'etiqueta amb l'id indicat. */
    @Override
    public void eliminar(Long id) {
        etiquetaRepository.deleteById(id);
    }
}
