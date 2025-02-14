package com.mio.progetto;

import com.mio.progetto.Model.Transazione;
import com.mio.progetto.Model.TransazioneEntity;

import java.util.List;
public class TransizioniService {
    private Transazione transazione = new Transazione();

    public double calcolaTotaleSpese() {
        List<TransazioneEntity> transazioni = transazione.getAllTransazioni();
        return transazioni.stream().mapToDouble(TransazioneEntity::getImporto).sum();
    }
}
