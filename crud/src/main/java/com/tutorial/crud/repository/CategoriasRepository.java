package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Integer> {
    Optional<Categorias> findByName(String name);

    boolean existsByName(String name);
}
