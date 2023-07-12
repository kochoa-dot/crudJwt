package com.tutorial.crud.controller;

import com.tutorial.crud.dto.CategoriasDto;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.entity.Categorias;
import com.tutorial.crud.service.CategoriasService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriasController {

    @Autowired
    CategoriasService categoriasService;

    @GetMapping("")
    public ResponseEntity<List<Categorias>> findAll(){
        List<Categorias> list = categoriasService.list();
        return new ResponseEntity<List<Categorias>>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        if (!categoriasService.existById(id))
            return new ResponseEntity<>(new Mensaje("La categoria solicitada no existe"), HttpStatus.NOT_FOUND);
        Categorias categorias = categoriasService.getOne(id).get();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/detail-name/{name}")
    public ResponseEntity<?> getByNombre(@PathVariable("name") String name){
        if(!categoriasService.existByNombre(name))
            return new ResponseEntity<Mensaje>(new Mensaje("La categoria con nombre " + name + " no existe"), HttpStatus.NOT_FOUND);
        Categorias categorias = categoriasService.getByNombre(name).get();
        return new ResponseEntity<Categorias>(categorias, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Mensaje> create(@RequestBody CategoriasDto categoriasDto){
        if (StringUtils.isBlank(categoriasDto.getName()))
            return new ResponseEntity<Mensaje>(new Mensaje("El nombre de la categoria es obligatorio"), HttpStatus.BAD_REQUEST);
        if (categoriasService.existByNombre(categoriasDto.getName()))
            return new ResponseEntity<Mensaje>(new Mensaje("El nombre " + categoriasDto.getName() + " ya se encuentra registrada"), HttpStatus.BAD_REQUEST);
        Categorias categorias = new Categorias(categoriasDto.getName());
        categoriasService.save(categorias);
        return new ResponseEntity<Mensaje>(new Mensaje("Categoría creada con éxito"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> update(@PathVariable("id") int id, @RequestBody CategoriasDto categoriasDto){
        if (!categoriasService.existById(id))
            return new ResponseEntity<Mensaje>(new Mensaje("La categoría no existe"), HttpStatus.NOT_FOUND);
        if (categoriasService.existByNombre(categoriasDto.getName()) && categoriasService.getByNombre(categoriasDto.getName()).get().getId() != id)
            return new ResponseEntity<Mensaje>(new Mensaje("El nombre " + categoriasDto.getName() + " ya se encuentra registrada"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(categoriasDto.getName()))
            return new ResponseEntity<Mensaje>(new Mensaje("El nombre de la categoría es obligatorio"), HttpStatus.BAD_REQUEST);

        Categorias categorias = categoriasService.getOne(id).get();
        categorias.setName(categoriasDto.getName());
        categoriasService.save(categorias);
        return new ResponseEntity<Mensaje>(new Mensaje("Categoría actualizada con éxito"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> delete(@PathVariable("id") int id){
        if (!categoriasService.existById(id))
            return new ResponseEntity<Mensaje>(new Mensaje("La categoría a eliminar no existe"), HttpStatus.NOT_FOUND);
        categoriasService.delete(id);
        return new ResponseEntity<Mensaje>(new Mensaje("Categoría eliminada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createMult")
    public ResponseEntity<Mensaje> createMult(@RequestBody List<CategoriasDto> categoriasDto) {
        for (CategoriasDto categoriaDto : categoriasDto) {
            if (StringUtils.isBlank(categoriaDto.getName()))
                return new ResponseEntity<>(new Mensaje("El nombre de la categoría es obligatorio"), HttpStatus.BAD_REQUEST);
            if (categoriasService.existByNombre(categoriaDto.getName()))
                return new ResponseEntity<>(new Mensaje("El nombre " + categoriaDto.getName() + " ya se encuentra registrado"), HttpStatus.BAD_REQUEST);

            Categorias categorias = new Categorias(categoriaDto.getName());
            categoriasService.save(categorias);
        }

        return new ResponseEntity<>(new Mensaje("Categorías creadas con éxito"), HttpStatus.CREATED);
    }
}
