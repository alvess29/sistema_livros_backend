package com.biblioteca.livro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LivroRequest(
        @NotBlank @Size(max = 120) String titulo,
        @NotBlank @Size(max = 120) String autor,
        @NotBlank @Size(max = 60) String genero,
        @NotBlank @Size(max = 500) String descricao,
        @Size(max = 200_000) @Pattern(regexp = "^(data:image/jpeg;base64,[A-Za-z0-9+/=]+)?$") String capa) {

    public Livro paraLivro() {
        return new Livro(titulo, autor, genero, descricao, capa);
    }
}
