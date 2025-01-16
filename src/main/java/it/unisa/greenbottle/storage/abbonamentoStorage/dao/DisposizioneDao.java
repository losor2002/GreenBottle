package it.unisa.greenbottle.storage.abbonamentoStorage.dao;

import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Disposizione;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisposizioneDao extends JpaRepository<Disposizione, Long> {

  Integer findQuantitaByProdotto(Prodotto prodotto);

  List<Disposizione> findByAbbonamento(Abbonamento abbonamento);
}