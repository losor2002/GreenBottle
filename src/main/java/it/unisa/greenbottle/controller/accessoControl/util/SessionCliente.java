package it.unisa.greenbottle.controller.accessoControl.util;

import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Classe SessionCliente, utilizzata per la gestione della sessione del cliente.
 */
@Component
@SessionScope
public class SessionCliente {

  private Long idCliente;

  @Autowired
  private ClienteDao clienteDao;

  /**
   * Metodo per ottenere il cliente.
   *
   * @return cliente
   */
  @ModelAttribute("cliente")
  public Optional<Cliente> getCliente() {
    if (idCliente != null) {
      return clienteDao.findClienteById(idCliente);
    }
    return Optional.empty();
  }

  public void setCliente(Cliente cliente) {
    this.idCliente = (cliente == null) ? null : cliente.getId();
  }

  public void emptyCliente() {
    this.idCliente = null;
  }
}
