package com.example.biblioteca.repository;

import com.example.biblioteca.model.Llibre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LlibreRepository extends JpaRepository<Llibre, Long> {
}