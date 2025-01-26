package it.unisa.greenbottle.controller.catalogoControl;

import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller per la gestione delle richieste relative ai prodotti.
 */
@Controller
@RequestMapping("/prodotto")
public class ProdottoController {

  @Autowired
  private ProdottoDao prodottoDao;

  /**
   * Metodo per ottenere un prodotto dal catalogo.
   *
   * @param id id del prodotto da cercare
   * @return ResponseEntity contenente il prodotto cercato
   */
  @GetMapping
  public ResponseEntity<?> get(@RequestParam Long id) {
    Optional<Prodotto> prodotto = prodottoDao.findProdottoById(id);
    if (prodotto.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Errore: Prodotto non trovato");
    } else {
      return ResponseEntity.ok(prodotto.get());
    }
  }
}
