package com.example.musica2.security;


import com.example.musica2.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    public SecurityFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("SecurityFilter is invoked");

        var token = this.recoverToken(request);

        try {
            if (token != null) {
                System.out.println("Token: " + token);
                var usuarioID = tokenService.validateToken(token);

                if (usuarioID != null && !usuarioID.isEmpty()) {
                    System.out.println("UsuarioID: " + usuarioID);

                    UserDetails user = usuarioService.findUsuarioByNome(usuarioID);

                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new AccessDeniedException("Erro ao autenticar.");
                }
            }

            filterChain.doFilter(request, response);

        } catch (AccessDeniedException ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
        System.out.println("SecurityFilter is finished");
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}