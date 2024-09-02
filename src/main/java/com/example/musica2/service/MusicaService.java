package com.example.musica2.service;

import com.example.musica2.model.Musica;
import com.example.musica2.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicaService {

    @Autowired
    private MusicaRepository musicaRepository;

    public List<Musica> findAll() {
        return musicaRepository.findAll();
    }

    public Optional<Musica> findById(Long id) {
        return musicaRepository.findById(id);
    }

    public Musica save(Musica musica) {
        return musicaRepository.save(musica);
    }

    public void deleteById(Long id) {
        musicaRepository.deleteById(id);
    }

}
