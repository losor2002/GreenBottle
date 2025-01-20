package it.unisa.greenbottle.storage.ordineStorage.dao;

import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dei dati delle composizioni nel database.
 */
public interface ComposizioneDao extends JpaRepository<Composizione, Long> {
  List<Composizione> findComposizioneByOrdine(Ordine ordine);
}
