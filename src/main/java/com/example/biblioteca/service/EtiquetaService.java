package com.example.biblioteca.service;

import com.example.biblioteca.model.Etiqueta;
import com.example.biblioteca.repository.EtiquetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaService {

    private final EtiquetaRepository etiquetaRepository;

    public EtiquetaService(EtiquetaRepository etiquetaRepository) {
        this.etiquetaRepository = etiquetaRepository;
    }

    public List<Etiqueta> llistarTotes() {
        return etiquetaRepository.findAll();
    }

    public Etiqueta guardar(Etiqueta etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }
}