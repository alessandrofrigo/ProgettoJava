package com.mio.progetto.Controller;

import com.mio.progetto.Model.TransazioneEntity;
import com.mio.progetto.Model.Categoria;
import com.mio.progetto.Service.TransazioneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransazioneController.class)
public class TransazioneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransazioneService transazioneService;

    @Test
    public void testGetAllTransazioni() throws Exception {
        TransazioneEntity t1 = new TransazioneEntity();
        t1.setId(1);
        t1.setDescrizione("Spesa");
        t1.setCategoria(Categoria.CIBO);
        t1.setSottocategoria("Supermercato");
        t1.setImporto(50.0);
        t1.setData("2023-10-10");

        when(transazioneService.getAllTransazioni()).thenReturn(Arrays.asList(t1));

        mockMvc.perform(get("/api/transazioni"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descrizione").value("Spesa"))
                .andExpect(jsonPath("$[0].importo").value(50.0))
                .andExpect(jsonPath("$[0].categoria").value("CIBO"));
    }
}
