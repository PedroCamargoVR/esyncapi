package br.com.pedrocamargo.esync.infra.security;

import br.com.pedrocamargo.esync.exceptions.TokenNotFoundException;
import br.com.pedrocamargo.esync.exceptions.TokenNotValidException;
import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.usuario.repository.UsuarioRepository;
import br.com.pedrocamargo.esync.infra.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = getHeaderToken(request);

            if(token != null && !request.getRequestURI().equals("/auth")){
                String subject = tokenService.validateToken(token);
                UserDetails usuario = usuarioRepository.findByUsuario(subject);

                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities()));
            }

            filterChain.doFilter(request, response);
        }catch (TokenNotFoundException | TokenNotValidException ex){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(new ObjectMapper().writeValueAsString(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), "Token JWT nao enviado ou invalido")));
            response.getWriter().flush();
            return;
        }
    }
    private String getHeaderToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ","");
        }
        return null;
    }
}
