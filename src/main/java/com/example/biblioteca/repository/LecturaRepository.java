package com.example.biblioteca.repository;

import com.example.biblioteca.model.Lectura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturaRepository extends JpaRepository<Lectura, Long> {
}