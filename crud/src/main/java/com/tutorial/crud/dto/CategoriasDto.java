package com.tutorial.crud.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CategoriasDto {

    @NotBlank
    private String name;

    public CategoriasDto(@NotBlank String name){
        this.name = name;
    }
}
