package it.unisa.greenbottle.storage.areaPersonaleStorage.dao;

import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dei dati degli indirizzi nel database.
 */
public interface IndirizzoDao extends JpaRepository<Indirizzo, Long> {
  Optional<Indirizzo> findIndirizzoById(Long idIndirizzo);
}
