package br.com.pedrocamargo.esync.exceptions;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String m){
        super(m);
    }
}
