package it.unisa.greenbottle;

import it.unisa.greenbottle.storage.abbonamentoStorage.dao.AbbonamentoDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.DisposizioneDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Disposizione;
import it.unisa.greenbottle.storage.catalogoStorage.dao.CategoriaDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AbbonamentoTest {
  @Autowired
  private AbbonamentoDao abbonamentoDao;
  @Autowired
  private DisposizioneDao disposizioneDao;
  @Autowired
  private ProdottoDao prodottoDao;
  @Autowired
  private CategoriaDao categoriaDao;

  @Test
  public void abbonamentiTier() {
    Abbonamento abbonamento1 = new Abbonamento(Abbonamento.TipoAbbonamento.BRONZE,
        Abbonamento.RinnovoAbbonamento.SEMESTRALE, Abbonamento.FrequenzaAbbonamento.MENSILE);
    Abbonamento abbonamento2 = new Abbonamento(Abbonamento.TipoAbbonamento.SILVER,
        Abbonamento.RinnovoAbbonamento.BIMESTRALE, Abbonamento.FrequenzaAbbonamento.SETTIMANALE);
    Abbonamento abbonamento3 =
        new Abbonamento(Abbonamento.TipoAbbonamento.GOLD, Abbonamento.RinnovoAbbonamento.MENSILE,
            Abbonamento.FrequenzaAbbonamento.GIORNALIERO);
    abbonamentoDao.save(abbonamento1);
    abbonamentoDao.save(abbonamento2);
    abbonamentoDao.save(abbonamento3);

    Categoria categoriaDefault = new Categoria("Bevande gassate");
    categoriaDao.save(categoriaDefault);
    Prodotto prodotto1 = new Prodotto("Fanta", "Bevanda", null, 2.5f, 100, null);
    Prodotto prodotto2 = new Prodotto("Coca Cola", "Bevanda", null, 1.5f, 100, null);
    Prodotto prodotto3 = new Prodotto("Sprite", "Bevanda", null, 2.0f, 100, categoriaDefault);
    prodottoDao.save(prodotto1);
    prodottoDao.save(prodotto2);
    prodottoDao.save(prodotto3);

    disposizioneDao.save(new Disposizione(1, prodotto1, abbonamento1));
    disposizioneDao.save(new Disposizione(2, prodotto2, abbonamento1));
    disposizioneDao.save(new Disposizione(3, prodotto3, abbonamento1));
    disposizioneDao.save(new Disposizione(2, prodotto1, abbonamento2));


    List<Abbonamento> abbonamenti = abbonamentoDao.findAbbonamentoByTipo(
        Abbonamento.TipoAbbonamento.BRONZE);
    HashMap<Abbonamento, List<Disposizione>> disposizione =
        new HashMap<Abbonamento, List<Disposizione>>();
    for (Abbonamento abbonamento : abbonamenti) {
      disposizione.put(abbonamento, disposizioneDao.findDisposizioneByAbbonamento(abbonamento));
    }

    System.out.println(disposizione);
    assert true;
  }
}
