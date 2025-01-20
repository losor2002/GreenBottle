package it.unisa.greenbottle.storage.abbonamentoStorage.dao;

import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Disposizione;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dei dati delle disposizioni.
 */
public interface DisposizioneDao extends JpaRepository<Disposizione, Long> {
  List<Disposizione> findDisposizioneByAbbonamento(Abbonamento abbonamento);
}