package it.unisa.greenbottle.storage.catalogoStorage.dao;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Recensione;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dei dati delle recensioni.
 */
public interface RecensioneDao extends JpaRepository<Recensione, Long> {
  Optional<Recensione> findRecensioneById(Long idRecensione);
}
