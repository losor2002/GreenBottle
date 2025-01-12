package it.unisa.greenbottle.storage.ordineStorage.dao;

import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComposizioneDao extends JpaRepository<Composizione, Long> {
  Set<Composizione> findByOrdineId(Long id);
}