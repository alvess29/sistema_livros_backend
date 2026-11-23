package com.biblioteca.livro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class ErroFirestoreException extends RuntimeException {

    public ErroFirestoreException(Throwable causa) {
        super("Falha ao acessar o Firestore", causa);
    }
}
