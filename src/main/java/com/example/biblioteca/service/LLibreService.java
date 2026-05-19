package com.example.biblioteca.service;

import com.example.biblioteca.model.Llibre;
import com.example.biblioteca.repository.LlibreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LlibreService {

    private final LlibreRepository llibreRepository;

    public LlibreService(LlibreRepository llibreRepository) {
        this.llibreRepository = llibreRepository;
    }

    public List<Llibre> llistarTots() {
        return llibreRepository.findAll();
    }

    public Optional<Llibre> buscarPerId(Long id) {
        return llibreRepository.findById(id);
    }

    public Llibre guardar(Llibre llibre) {
        return llibreRepository.save(llibre);
    }

    public void eliminar(Long id) {
        llibreRepository.deleteById(id);
    }
}
