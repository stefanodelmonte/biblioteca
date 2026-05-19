package com.example.biblioteca.service;

import com.example.biblioteca.model.Categoria;
import com.example.biblioteca.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servei per gestionar les operacions CRUD de Categoria.
 */
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /** Retorna totes les categories de la base de dades. */
    public List<Categoria> llistarTotes() {
        return categoriaRepository.findAll();
    }

    /** Busca una categoria per id. */
    public Optional<Categoria> buscarPerId(Long id) {
        return categoriaRepository.findById(id);
    }

    /** Desa una categoria nova o actualitza una d'existent. */
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    /** Elimina la categoria amb l'id indicat. */
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}