package it.unisa.greenbottle.OrdineTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.form.OrdineForm;
import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.dao.IndirizzoDao;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import it.unisa.greenbottle.storage.catalogoStorage.dao.CategoriaDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@AutoConfigureMockMvc
@SpringBootTest
public class CreazioneOrdineWTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private OrdineForm ordineForm;

  @MockitoBean
  private OrdineDao ordineDao;

  @MockitoBean
  private SessionCarrello sessionCarrello;

  @MockitoBean
  private ProdottoDao prodottoDao;

  @MockitoBean
  private SessionCliente sessionCliente;

  @MockitoBean
  private IndirizzoDao indirizzoDao;

  @Test
  public void nomeNonValido() throws Exception {
    testCreazioneOrdine("A1", "0000000000000000", "99/99", "ABC", 55L, null, Optional.of("asd"),
        status().isBadRequest());
  }

  @Test
  public void numeroNonValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "0000000000000000", "99/99", "ABC", 55L, null,
        Optional.of("asd"), status().isBadRequest());
  }

  @Test
  public void dataNonValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "99/99", "ABC", 55L, null,
        Optional.of("asd"), status().isBadRequest());
  }

  @Test
  public void CVVNonValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "ABC", 55L, null,
        Optional.of("asd"), status().isBadRequest());
  }

  @Test
  public void indirizzoNonValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", -12L, null,
        Optional.of("asd"), status().isBadRequest());
  }

  @Test
  public void indirizzoNonPresente() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", 7355608L, null,
        Optional.of("asd"), status().is3xxRedirection());
  }

  @Test
  public void isSupportoNonValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", 1L, null,
        Optional.of("asd"), status().is3xxRedirection());
  }

  @Test
  public void descrizioneNonValida() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", 1L, true,
        Optional.of(("a").repeat(320)), status().isBadRequest());
  }

  @Test
  public void descrizioneNonPresente() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", 1L, true,
        Optional.of(""), status().isBadRequest());
  }

  @Test
  public void ordineSupportoValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", 1L, true,
        Optional.of("Consegna al primo piano"), status().is3xxRedirection());
  }

  @Test
  public void ordineValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", 1L, false,
        Optional.of(""), status().is3xxRedirection());
  }

  private void testCreazioneOrdine(String Nome_titolare, String Numero_carta, String Data_scadenza,
                                   String CVV, Long idIndirizzo, Boolean isSupporto,
                                   Optional<String> Descrizione_Supporto,
                                   ResultMatcher resultmatcher)
      throws Exception {
    Cliente cliente = new Cliente();
    cliente.setId(1L);
    Indirizzo indirizzo = new Indirizzo();
    Map<Long, Integer> carrello = new HashMap<>();
    Categoria categoria = new Categoria("Bevande");
    Prodotto prodotto1 = new Prodotto(
        "Prodotto1", "Prodotto 1", null, 10.0f, 1000, categoria
    );
    Prodotto prodotto2 = new Prodotto(
        "Prodotto2", "Prodotto 2", null, 5.0f, 1000, categoria
    );

    carrello.put(prodotto1.getId(), 10);
    carrello.put(prodotto2.getId(), 50);
    indirizzo.setId(idIndirizzo);
    ordineForm =
        new OrdineForm(Numero_carta, Data_scadenza, CVV, Nome_titolare, idIndirizzo, isSupporto,
            true, Descrizione_Supporto.orElse(""));

    when(sessionCliente.getCliente()).thenReturn(Optional.of(cliente));
    when(sessionCarrello.getCarrello()).thenReturn(carrello);
    when(indirizzoDao.findIndirizzoById(idIndirizzo)).thenReturn(
        idIndirizzo > 0 ? Optional.of(indirizzo) : Optional.empty());
    when(prodottoDao.findProdottoById(prodotto1.getId())).thenReturn(Optional.of(prodotto1));
    when(prodottoDao.findProdottoById(prodotto2.getId())).thenReturn(Optional.of(prodotto2));
    //isRitiro non va testato secondo il TCS.

    mockMvc.perform(post("/ordina")
        .param("nomeTitolare", ordineForm.getNomeTitolare())
        .param("numeroCarta", ordineForm.getNumeroCarta())
        .param("dataScadenza", ordineForm.getDataScadenza())
        .param("CVV", ordineForm.getCVV())
        .param("indirizzo", ordineForm.getIndirizzo().toString())
        .param("isSupporto",
            ordineForm.getIsSupporto() == null ? null : ordineForm.getIsSupporto().toString())
        .param("isRitiro", "false")
        .param("descrizioneSupporto", Descrizione_Supporto.orElse(""))
    ).andExpect(resultmatcher);


  }


}
