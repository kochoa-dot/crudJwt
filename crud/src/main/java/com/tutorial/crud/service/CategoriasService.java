package com.tutorial.crud.service;

import com.tutorial.crud.entity.Categorias;
import com.tutorial.crud.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriasService {

    @Autowired
    CategoriasRepository categoriasRepository;

    public List<Categorias> list(){
        return categoriasRepository.findAll();
    }

    public Optional<Categorias> getOne(int id){
        return categoriasRepository.findById(id);
    }

    public Optional<Categorias> getByNombre(String name){
        return categoriasRepository.findByName(name);
    }

    public void save(Categorias categorias){
        categoriasRepository.save(categorias);
    }

    public void delete(int id){
        categoriasRepository.deleteById(id);
    }

    public boolean existById(int id){
        return categoriasRepository.existsById(id);
    }

    public boolean existByNombre(String name){
        return categoriasRepository.existsByName(name);
    }
}
