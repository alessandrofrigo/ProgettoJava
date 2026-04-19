package com.mio.progetto.Repository;

import com.mio.progetto.Model.Categoria;
import com.mio.progetto.Model.TransazioneEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TransazioneRepository {

    private static final Logger log = LoggerFactory.getLogger(TransazioneRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public TransazioneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<TransazioneEntity> rowMapper = (ResultSet rs, int rowNum) -> {
        TransazioneEntity t = new TransazioneEntity();
        t.setId(rs.getInt("id"));
        t.setDescrizione(rs.getString("descrizione"));
        t.setCategoria(Categoria.valueOf(rs.getString("categoria")));
        t.setSottocategoria(rs.getString("sottocategoria"));
        t.setImporto(rs.getBigDecimal("importo"));
        t.setData(rs.getDate("data").toLocalDate());
        return t;
    };

    public List<TransazioneEntity> findAll() {
        String sql = "SELECT * FROM transazioni";
        List<TransazioneEntity> risultati = jdbcTemplate.query(sql, rowMapper);
        log.info("Recuperate {} transazioni dal database", risultati.size());
        return risultati;
    }

    public int insert(TransazioneEntity t) {
        String sql = "INSERT INTO transazioni (descrizione, importo, categoria, sottocategoria, data) VALUES (?, ?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql,
                t.getDescrizione(),
                t.getImporto(),
                t.getCategoria().name(),
                t.getSottocategoria(),
                t.getData()
        );
        log.info("Transazione inserita con successo: {}", t.getDescrizione());
        return rows;
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM transazioni WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            log.info("Transazione con ID {} eliminata", id);
        } else {
            log.warn("Nessuna transazione trovata con ID: {}", id);
        }
        return rows;
    }

    public int deleteByCategoria(String categoria) {
        String sql = "DELETE FROM transazioni WHERE categoria = ?";
        int rows = jdbcTemplate.update(sql, categoria);
        log.info("{} transazioni eliminate nella categoria: {}", rows, categoria);
        return rows;
    }

    public int deleteBeforeDate(String data) {
        String sql = "DELETE FROM transazioni WHERE data < ?";
        int rows = jdbcTemplate.update(sql, data);
        log.info("{} transazioni eliminate prima del {}", rows, data);
        return rows;
    }
}
