CREATE DATABASE IF NOT EXISTS gestione_spese;

USE gestione_spese;

CREATE TABLE IF NOT EXISTS transazioni (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descrizione VARCHAR(255),
    importo DECIMAL(10,2),
    categoria VARCHAR(50),
    sottocategoria VARCHAR(50),
    data DATE
);
