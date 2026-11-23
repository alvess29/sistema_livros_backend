package com.biblioteca.livro;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

@Repository
public class LivroRepository {

    private final CollectionReference livros;

    public LivroRepository(Firestore firestore) {
        this.livros = firestore.collection("livros");
    }

    public List<Livro> listar() {
        return aguardar(livros.orderBy("titulo").get()).getDocuments().stream()
                .map(documento -> documento.toObject(Livro.class))
                .toList();
    }

    public Livro salvar(Livro livro) {
        DocumentReference documento = livros.document();
        aguardar(documento.set(livro));
        livro.setId(documento.getId());
        return livro;
    }

    public Optional<Livro> atualizar(String id, Livro livro) {
        DocumentReference documento = livros.document(id);
        if (!existe(documento)) {
            return Optional.empty();
        }
        aguardar(documento.set(livro));
        livro.setId(id);
        return Optional.of(livro);
    }

    public boolean excluir(String id) {
        DocumentReference documento = livros.document(id);
        if (!existe(documento)) {
            return false;
        }
        aguardar(documento.delete());
        return true;
    }

    private boolean existe(DocumentReference documento) {
        return aguardar(documento.get()).exists();
    }

    private <T> T aguardar(ApiFuture<T> futuro) {
        try {
            return futuro.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ErroFirestoreException(e);
        } catch (ExecutionException e) {
            throw new ErroFirestoreException(e);
        }
    }
}
