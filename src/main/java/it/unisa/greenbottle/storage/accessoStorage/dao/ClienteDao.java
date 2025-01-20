package it.unisa.greenbottle.storage.accessoStorage.dao;

import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dei dati dei clienti.
 */
public interface ClienteDao extends JpaRepository<Cliente, Long> {
  Optional<Cliente> findClienteById(Long idCliente);

  Optional<Cliente> findClienteByEmail(String email);
}