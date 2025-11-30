package com.mio.progetto.Service;

import com.mio.progetto.Model.Transazione;
import com.mio.progetto.Model.TransazioneEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransazioneService {

    private final Transazione transazioneModel;

    public TransazioneService() {
        this.transazioneModel = new Transazione();
    }

    public List<TransazioneEntity> getAllTransazioni() {
        return transazioneModel.getAllTransazioni();
    }

    public void insertTransazione(TransazioneEntity transazioneEntity) {
        transazioneModel.insertTransizione(transazioneEntity);
    }

    public void deleteTransazioneById(int id) {
        transazioneModel.deleteTransazioneById(id);
    }

    public void deleteTransazioniByCategoria(String categoria) {
        transazioneModel.deleteTransazioniByCategoria(categoria);
    }

    public void deleteTransazioniBeforeDate(String data) {
        transazioneModel.deleteTransazioniBeforeDate(data);
    }
}
