package it.unisa.greenbottle.storage.areaPersonaleStorage.dao;

import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndirizzoDao extends JpaRepository<Indirizzo, Long> {

  Optional<Indirizzo> findIndirizzoById(Long idIndirizzo);
}
