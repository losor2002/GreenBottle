package it.unisa.greenbottle.controller.accessoControl.util;

import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionCliente {

  private Long idCliente;

  @Autowired
  private ClienteDao clienteDao;

  public Optional<Cliente> getCliente() {
    if (idCliente != null) {
      return clienteDao.findById(idCliente);
    }
    return Optional.empty();
  }

  public void setCliente(Cliente cliente) {
    if (cliente == null) {
      this.idCliente = null;
    } else {
      this.idCliente = cliente.getId();
    }
  }
}