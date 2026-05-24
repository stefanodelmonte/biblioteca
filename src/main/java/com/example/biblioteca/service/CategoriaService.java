package com.example.biblioteca.service;

import com.example.biblioteca.model.Categoria;
import com.example.biblioteca.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servei per gestionar les operacions CRUD de Categoria.
 * Implementa ServeiCrud per garantir la interfície comuna (RA7).
 */
@Service
public class CategoriaService implements ServeiCrud<Categoria> {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /** Retorna totes les categories de la base de dades. */
    @Override
    public List<Categoria> llistarTots() {
        return categoriaRepository.findAll();
    }

    /** Busca una categoria per id. */
    @Override
    public Optional<Categoria> buscarPerId(Long id) {
        return categoriaRepository.findById(id);
    }

    /** Desa una categoria nova o actualitza una d'existent. */
    @Override
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    /** Elimina la categoria amb l'id indicat. */
    @Override
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}
