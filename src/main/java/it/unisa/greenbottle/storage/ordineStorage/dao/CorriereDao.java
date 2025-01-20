package it.unisa.greenbottle.storage.ordineStorage.dao;

import it.unisa.greenbottle.storage.ordineStorage.entity.Corriere;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dei corrieri nel database.
 */
public interface CorriereDao extends JpaRepository<Corriere, Long> {
  Corriere findCorriereById(Long id);
}
