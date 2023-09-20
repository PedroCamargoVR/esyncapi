package br.com.pedrocamargo.esync.exceptions;

public class TokenNotValidException extends RuntimeException {
    public TokenNotValidException(String m){
        super(m);
    }
}
