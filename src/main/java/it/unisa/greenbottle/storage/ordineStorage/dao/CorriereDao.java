package it.unisa.greenbottle.storage.ordineStorage.dao;

import it.unisa.greenbottle.storage.ordineStorage.entity.Corriere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorriereDao extends JpaRepository<Corriere, Long> {
}