package it.unisa.greenbottle.storage.ordineStorage.dao;

import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComposizioneDao extends JpaRepository<Composizione, Long> {
  List<Composizione> findByOrdine(Ordine ordine);

}