package com.mio.progetto.Service;

import com.mio.progetto.Model.Transazione;
import com.mio.progetto.Model.TransazioneEntity;
import com.mio.progetto.Model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransazioneServiceTest {

    @Mock
    private Transazione transazioneModel;

    @InjectMocks
    private TransazioneService transazioneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTransazioni() {
        TransazioneEntity t1 = new TransazioneEntity(1, "Spesa", Categoria.CIBO, "Supermercato", 50.0, "2023-10-10");
        TransazioneEntity t2 = new TransazioneEntity(2, "Cinema", Categoria.INTRATTENIMENTO, "Film", 15.0, "2023-10-12");
        when(transazioneModel.getAllTransazioni()).thenReturn(Arrays.asList(t1, t2));

        List<TransazioneEntity> result = transazioneService.getAllTransazioni();

        assertEquals(2, result.size());
        assertEquals("Spesa", result.get(0).getDescrizione());
        verify(transazioneModel, times(1)).getAllTransazioni();
    }

    @Test
    void testCalcolaTotaleSpese() {
        TransazioneEntity t1 = new TransazioneEntity();
        t1.setImporto(50.0);
        TransazioneEntity t2 = new TransazioneEntity();
        t2.setImporto(15.0);
        
        when(transazioneModel.getAllTransazioni()).thenReturn(Arrays.asList(t1, t2));

        double totale = transazioneService.calcolaTotaleSpese();

        assertEquals(65.0, totale);
    }

    @Test
    void testInsertTransazione() {
        TransazioneEntity t1 = new TransazioneEntity();
        transazioneService.insertTransazione(t1);
        verify(transazioneModel, times(1)).insertTransizione(t1);
    }
}
