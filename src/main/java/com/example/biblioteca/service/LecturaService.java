package com.example.biblioteca.service;

import com.example.biblioteca.model.Lectura;
import com.example.biblioteca.repository.LecturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LecturaService {

    private final LecturaRepository lecturaRepository;

    public LecturaService(LecturaRepository lecturaRepository) {
        this.lecturaRepository = lecturaRepository;
    }

    public List<Lectura> llistarTotes() {
        return lecturaRepository.findAll();
    }

    public Optional<Lectura> buscarPerId(Long id) {
        return lecturaRepository.findById(id);
    }

    public Lectura guardar(Lectura lectura) {
        return lecturaRepository.save(lectura);
    }

    public void eliminar(Long id) {
        lecturaRepository.deleteById(id);
    }
}
