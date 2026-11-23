package com.biblioteca.livro;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroRepository repository;

    public LivroController(LivroRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/list")
    public List<Livro> listar() {
        return repository.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro salvar(@Valid @RequestBody LivroRequest request) {
        return repository.salvar(request.paraLivro());
    }

    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable String id, @Valid @RequestBody LivroRequest request) {
        return repository.atualizar(id, request.paraLivro())
                .orElseThrow(this::livroNaoEncontrado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable String id) {
        if (!repository.excluir(id)) {
            throw livroNaoEncontrado();
        }
    }

    private ResponseStatusException livroNaoEncontrado() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
    }
}
