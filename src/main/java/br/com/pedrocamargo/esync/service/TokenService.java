package br.com.pedrocamargo.esync.service;

import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class TokenService {

    @Value("${spring.auth.token.secret}")
    private String secret;
    public String generateToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .create()
                    .withIssuer("esync")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(expireToken())
                    .sign(algorithm);
        }catch(Exception e){
            throw new RuntimeException("Erro ao gerar Token JWT");
        }
    }
    private Instant expireToken(){
        return LocalDateTime.now(ZoneOffset.of("-03:00")).plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
