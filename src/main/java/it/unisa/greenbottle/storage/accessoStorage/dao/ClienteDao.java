package it.unisa.greenbottle.storage.accessoStorage.dao;

import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDao extends JpaRepository<Cliente, Long> {
  Optional<Cliente> findClienteByEmail(String email);

  Optional<Cliente> findClienteById(Long id);
}
