package it.unisa.greenbottle;

import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.dao.IndirizzoDao;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import it.unisa.greenbottle.storage.ordineStorage.dao.ComposizioneDao;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import it.unisa.greenbottle.storage.ordineStorage.entity.OrdineDirector;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestOrdine {

  @Autowired
  private OrdineDao ordineDao;

  @Autowired
  private IndirizzoDao indirizzoDao;

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private ClienteDao clienteDao;

  @Autowired
  private ComposizioneDao composizioneDao;

  // Oggetti di test (indipendenti dal database)
  private Cliente clienteTest =
      new Cliente("mario.rossi@gmail.com", "asdfASDF1234!", "Mario", "Rossi", 0, 0, null, null);

  private Indirizzo indirizzoTest = new Indirizzo("Via Roma", 5, "Potenza", "PZ", "85100");

  private Categoria categoriaTest = new Categoria("Bevande");
  private Prodotto prodottoTest =
      new Prodotto("Acqua minerale", "Acqua minerale naturale", null, 0.5f, 1000, categoriaTest);

  private Set<Composizione> composizioniTest = new HashSet<>();

  @Test
  public void createOrdine() {
    // Prepara l'ambiente (crea oggetti di test)
    clienteDao.save(clienteTest);
    indirizzoDao.save(indirizzoTest);
    prodottoDao.save(prodottoTest);

    // Aggiungi un prodotto al carrello
    composizioniTest.add(new Composizione(prodottoTest, 2));

    // Creazione dell'ordine
    float prezzoTotale = (float) composizioniTest.stream()
        .mapToDouble(c -> c.getProdotto().getPrezzo() * c.getQuantita())
        .sum();

    Ordine ordineTest = OrdineDirector.createOrdineConSupporto(
        prezzoTotale,
        false,               // isRitiro
        "1234-5678-9876-5432", // numero carta (un esempio)
        "Supporto Aggiuntivo", // descrizione supporto
        indirizzoTest,
        composizioniTest);

    ordineDao.save(ordineTest);

    System.out.println("Ordine test: " + ordineTest);


    Optional<Ordine> o2 = ordineDao.findById(ordineTest.getId());

    System.out.println(o2.isPresent() ? o2.get().toString() : "Not found");
    assert o2.isPresent() && ordineTest.equals(o2.get());
  }

  @Test
  public void createAndVerifyOrdineAndComposizione() {
    clienteDao.save(clienteTest);
    indirizzoDao.save(indirizzoTest);
    prodottoDao.save(prodottoTest);

    composizioniTest.add(new Composizione(prodottoTest, 2));
    composizioniTest.add(new Composizione(prodottoTest, 100));

    float prezzoTotale = (float) composizioniTest.stream()
        .mapToDouble(c -> c.getProdotto().getPrezzo() * c.getQuantita())
        .sum();

    Ordine ordineTest = OrdineDirector.createOrdineConSupporto(
        prezzoTotale,
        false,
        "1234-5678-9876-5432",
        "Supporto Aggiuntivo",
        indirizzoTest,
        composizioniTest);

    ordineDao.save(ordineTest);

    Optional<Ordine> retrievedOrdine = ordineDao.findById(ordineTest.getId());
    assert retrievedOrdine.isPresent();

    Ordine savedOrdine = retrievedOrdine.get();
    assert savedOrdine.getComposizioni() != null && !savedOrdine.getComposizioni().isEmpty();
    System.out.println(savedOrdine.getComposizioni());
    for (Composizione composizione : savedOrdine.getComposizioni()) {
      System.out.println(composizione.getOrdine() + " = " + savedOrdine);
      System.out.println(composizione.getProdotto() + " = " + prodottoTest);

      assert composizione.getOrdine().equals(savedOrdine);
      assert composizione.getProdotto().equals(prodottoTest);
    }
  }

}
