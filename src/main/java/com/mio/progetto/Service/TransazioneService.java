package com.mio.progetto.service;

import com.mio.progetto.model.TransazioneEntity;
import com.mio.progetto.repository.TransazioneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransazioneService {

    private static final Logger log = LoggerFactory.getLogger(TransazioneService.class);

    private final TransazioneRepository transazioneRepository;

    public TransazioneService(TransazioneRepository transazioneRepository) {
        this.transazioneRepository = transazioneRepository;
    }

    public List<TransazioneEntity> getAllTransazioni() {
        return transazioneRepository.findAll();
    }

    public void insertTransazione(TransazioneEntity transazioneEntity) {
        transazioneRepository.insert(transazioneEntity);
    }

    public int deleteTransazioneById(int id) {
        return transazioneRepository.deleteById(id);
    }

    public int deleteTransazioniByCategoria(String categoria) {
        return transazioneRepository.deleteByCategoria(categoria);
    }

    public int deleteTransazioniBeforeDate(String data) {
        return transazioneRepository.deleteBeforeDate(data);
    }
}
