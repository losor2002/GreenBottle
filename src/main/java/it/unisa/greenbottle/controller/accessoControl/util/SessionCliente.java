package it.unisa.greenbottle.controller.accessoControl.util;

import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;

import java.util.Map;
import java.util.Optional;

import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionCliente {

  private Long idCliente;
  @Getter
  private Map<Prodotto, Integer> Carrello;

  @Autowired
  private ClienteDao clienteDao;

  @Autowired
  private ProdottoDao prodottoDao;



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

  public Optional<Map<Prodotto, Integer>> getCarrello() {
    if (idCliente != null) {
      return Optional.ofNullable(Carrello);
    }
    return Optional.empty();
  }

  public void setCarrello(Map<Prodotto, Integer> carrello) {
    if (idCliente != null) {Carrello = carrello;}
  }

  public void addToCarrello(Prodotto prodotto, Integer quantita) {

    if (idCliente != null) {Carrello.put(prodotto, quantita);}
  }

  public void removeFromCarrello(Prodotto prodotto) {
    if (idCliente != null) {Carrello.remove(prodotto);}
  }

  public void emptyCarrello() {
    if (idCliente != null) {Carrello.clear();}
  }


}