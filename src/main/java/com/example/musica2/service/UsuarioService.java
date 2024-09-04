// src/main/java/com/example/musica2/service/UsuarioService.java
package com.example.musica2.service;

import com.example.musica2.model.Usuario;
import com.example.musica2.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        // Certifique-se de que a senha está codificada usando BCryptPasswordEncoder
        if (!usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public UserDetails findUsuarioByNome(String nome) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByNome(nome);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            return (UserDetails) usuario;
        } else {
            // Lidar com o caso em que o usuário não é encontrado
            throw new UsernameNotFoundException("User not found with name: " + nome);
        }
    }

    public Optional<Usuario> findByNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }
}