package com.example.musica2.service;

import com.example.musica2.model.Banda;
import com.example.musica2.repository.BandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BandaService {

    @Autowired
    private BandaRepository bandaRepository;

    public List<Banda> findAll() {
        return bandaRepository.findAll();
    }

    public Optional<Banda> findById(Long id) {
        return bandaRepository.findById(id);
    }

    public Banda save(Banda banda) {
        return bandaRepository.save(banda);
    }

    public void deleteById(Long id) {
        bandaRepository.deleteById(id);
    }

}
