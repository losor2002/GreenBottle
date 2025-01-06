package it.unisa.greenbottle.storage.ordineStorage.dao;

import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineDao extends JpaRepository<Ordine, Long> {
}
