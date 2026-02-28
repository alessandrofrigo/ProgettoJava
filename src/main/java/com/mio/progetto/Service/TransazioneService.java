package com.mio.progetto.Service;

import com.mio.progetto.Model.Transazione;
import com.mio.progetto.Model.TransazioneEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransazioneService {

    private final Transazione transazioneModel;

    @Autowired
    public TransazioneService(Transazione transazioneModel) {
        this.transazioneModel = transazioneModel;
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

    public double calcolaTotaleSpese() {
        List<TransazioneEntity> transazioni = transazioneModel.getAllTransazioni();
        return transazioni.stream().mapToDouble(TransazioneEntity::getImporto).sum();
    }
}
