package it.unisa.greenbottle.storage.ordineStorage.dao;

import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineDao extends JpaRepository<Ordine, Long> {
  List<Ordine> findOrdineByStato(Ordine.StatoSpedizione statoSpedizione);

  List<Ordine> findOrdineByCliente(Cliente cliente);

  Optional<Ordine> findOrdineById(Long idOrdine);
}
