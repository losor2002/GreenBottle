package it.unisa.greenbottle.storage.ordineStorage.dao;

import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineDao extends JpaRepository<Ordine, Long> {
  List<Ordine> findByStato(Ordine.StatoSpedizione statoSpedizione);
}
