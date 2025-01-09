package it.unisa.greenbottle.storage.abbonamentoStorage.dao;

import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbbonamentoDao extends JpaRepository<Abbonamento, Long> {
}
