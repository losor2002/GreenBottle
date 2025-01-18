package it.unisa.greenbottle.controller.accessoControl.util;

import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionCliente {

  private Long idCliente;

  private Map<Long, Integer> carrello;

  @Autowired
  private ClienteDao clienteDao;

  @Autowired
  private ProdottoDao prodottoDao;


  public Optional<Cliente> getCliente() {
    if (idCliente != null) {
      return clienteDao.findClienteById(idCliente);
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

  public void setCliente(Long idCliente) {
    this.idCliente = idCliente;
  }

  public Optional<Map<Long, Integer>> getCarrello() {
    if (idCliente != null) {
      return Optional.ofNullable(carrello);
    }
    return Optional.empty();
  }

  public void setCarrello(Map<Long, Integer> carrello) {
    if (idCliente != null) {
      this.carrello = carrello;
    }
  }

  public void addToCarrello(Long prodotto, Integer quantita) {
    if (idCliente != null) {
      Optional<Prodotto> prod = prodottoDao.findProdottoById(prodotto);
      if (prod.isPresent()) {
        if (carrello.containsKey(prodotto)) {
          carrello.put(prodotto, carrello.get(prodotto) + quantita);
        } else {
          carrello.put(prodotto, quantita);
        }
      }
    }

  }

  public void removeAllFromCarrello(Long idProdotto) {
    if (idCliente != null) {
      carrello.remove(idProdotto);
    }
  }

  public void removeOneFromCarrello(Long idProdotto) {
    if (idCliente != null) {
      if (carrello.containsKey(idProdotto)) {
        carrello.put(idProdotto, carrello.get(idProdotto) - 1);
        if (carrello.get(idProdotto) == 0) {
          carrello.remove(idProdotto);
        }
      }
    }
  }

  public void emptyCarrello() {
    if (idCliente != null) {
      carrello.clear();
    }
  }


}