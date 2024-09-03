package com.example.musica2.service;

import com.example.musica2.model.Usuario;
import com.example.musica2.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
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
        // Handle the case where the user is not found
        throw new UsernameNotFoundException("User not found with name: " + nome);
    }
}


}
