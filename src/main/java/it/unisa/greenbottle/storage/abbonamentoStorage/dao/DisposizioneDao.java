package it.unisa.greenbottle.storage.abbonamentoStorage.dao;

import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Disposizione;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface DisposizioneDao extends JpaRepository<Disposizione, Long> {

    List<Prodotto> findByAbbonamento(Abbonamento abbonamento);
    Map<Prodotto, Integer> findByAllByAbbonamentoAndTipo(Abbonamento abbonamento, Abbonamento.TipoAbbonamento tipoAbbonamento);
}
