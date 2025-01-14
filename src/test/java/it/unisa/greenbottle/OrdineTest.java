package it.unisa.greenbottle;

import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.dao.IndirizzoDao;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import it.unisa.greenbottle.storage.ordineStorage.entity.OrdineDirector;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrdineTest {

  @Autowired
  private OrdineDao ordineDao;

  @Autowired
  private IndirizzoDao indirizzoDao;

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private ClienteDao clienteDao;

  private final Cliente clienteTest =
      new Cliente("mario.rossi@gmail.com", "asdfASDF1234!", "Mario", "Rossi", 0, 0, null, null);

  private final Indirizzo indirizzoTest = new Indirizzo("Via Roma", 5, "Potenza", "PZ", "85100");

  private final Categoria categoriaTest = new Categoria("Bevande");
  private final Prodotto prodottoTest =
      new Prodotto("Acqua minerale", "Acqua minerale naturale", null, 0.5f, 1000, categoriaTest);

  private final Prodotto[] arrayProdottiTest = new Prodotto[] {
      prodottoTest,
      new Prodotto("Acqua frizzante", "Acqua frizzante naturale", null, 0.5f, 1000, categoriaTest),
      new Prodotto("Acqua tonica", "Acqua tonica naturale", null, 0.5f, 1000, categoriaTest),
      new Prodotto("Acqua gassata", "Acqua gassata naturale", null, 0.5f, 1000, categoriaTest),
      new Prodotto("Acqua liscia", "Acqua liscia naturale", null, 0.5f, 1000, categoriaTest),
      new Prodotto("Fantasia", "Fantasia naturale", null, 0.5f, 1000, categoriaTest),
      new Prodotto("Acqua intostata", "Acqua intostata naturale", null, 0.5f, 1000, categoriaTest),
      new Prodotto("Aranzata", "Aranzata naturale", null, 0.5f, 1000, categoriaTest)
  };
  private final List<Prodotto> prodottiTest = Arrays.asList(arrayProdottiTest);
  private final Set<Composizione> composizioniTest = new HashSet<>();

  @Test
  public void createOrdine() {

    clienteDao.save(clienteTest);
    indirizzoDao.save(indirizzoTest);
    prodottoDao.save(prodottoTest);

    composizioniTest.add(new Composizione(prodottoTest, 2));

    float prezzoTotale = (float) composizioniTest.stream()
        .mapToDouble(c -> c.getProdotto().getPrezzo() * c.getQuantita())
        .sum();

    Ordine ordineTest = OrdineDirector.createOrdineConSupporto(
        prezzoTotale,
        false,
        "1234-5678-9876-5432",
        "Supporto Aggiuntivo",
        indirizzoTest,
        clienteTest,
        composizioniTest);

    ordineDao.save(ordineTest);

    Optional<Ordine> o2 = ordineDao.findById(ordineTest.getId());

    assert o2.isPresent() && ordineTest.equals(o2.get());

    System.out.println("Saved Ordine: " + o2.get());
  }

  @Test
  public void createAndVerifyOrdineAndComposizione() {
    clienteDao.save(clienteTest);
    indirizzoDao.save(indirizzoTest);
    prodottoDao.saveAll(prodottiTest);

    composizioniTest.add(new Composizione(prodottoTest, 2));
    composizioniTest.add(new Composizione(prodottiTest.get(1), 100));

    float prezzoTotale = (float) composizioniTest.stream()
        .mapToDouble(c -> c.getProdotto().getPrezzo() * c.getQuantita())
        .sum();

    Ordine ordineTest = OrdineDirector.createOrdineConSupporto(
        prezzoTotale,
        false,
        "1234-5678-9876-5432",
        "Supporto Aggiuntivo",
        indirizzoTest,
        clienteTest,
        composizioniTest);

    ordineDao.save(ordineTest);

    Optional<Ordine> retrievedOrdine = ordineDao.findById(ordineTest.getId());
    assert retrievedOrdine.isPresent();

    Ordine savedOrdine = retrievedOrdine.get();

    assert savedOrdine.getComposizioni() != null && !savedOrdine.getComposizioni().isEmpty();
    System.out.println("Retrieved Composizioni: " + savedOrdine.getComposizioni());

    for (Composizione composizione : savedOrdine.getComposizioni()) {
      Prodotto prodotto = composizione.getProdotto();
      assert prodottiTest.contains(prodotto);

      System.out.println("Composizione: " + composizione);
      System.out.println("Product in Composizione: " + prodotto);
    }
  }
}
