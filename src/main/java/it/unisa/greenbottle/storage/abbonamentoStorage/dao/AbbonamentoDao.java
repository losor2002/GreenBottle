package it.unisa.greenbottle.storage.abbonamentoStorage.dao;

import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dei dati degli abbonamenti.
 */
public interface AbbonamentoDao extends JpaRepository<Abbonamento, Long> {
  Optional<Abbonamento> findAbbonamentoById(Long id);

  List<Abbonamento> findAbbonamentoByTipo(Abbonamento.TipoAbbonamento tipoAbbonamento);
}
