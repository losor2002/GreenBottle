package it.unisa.greenbottle.ordineTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.form.OrdineForm;
import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.dao.IndirizzoDao;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
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

/**
 * Testa la funzionalità di creazione di un ordine.
 */
@AutoConfigureMockMvc
@SpringBootTest
public class CreazioneOrdineTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private OrdineForm ordineForm;

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
    testCreazioneOrdine("A1", "5032123166322313", "02/26", "123", "1", "false", Optional.of("asd"),
        status().isBadRequest(), "Nome del titolare della carta non valido.");
  }

  @Test
  public void numeroNonValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "00000000000000000", "02/26", "123", "1", "false",
        Optional.of("asd"), status().isBadRequest(), "Numero di carta non valido.");
  }

  @Test
  public void dataNonValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "99/99", "123", "1", "false",
        Optional.of("asd"), status().isBadRequest(), "Data di scadenza della carta non valida.");
  }

  @Test
  public void cvvNonValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "ABC", "1", "false",
        Optional.of(""), status().isBadRequest(), "cvv non valido.");
  }

  @Test
  public void indirizzoNonValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", "-12", "false",
        Optional.of("asd"), status().isBadRequest(), "Indirizzo non valido.");
  }

  @Test
  public void indirizzoNonPresente() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", "7355608", "false",
        Optional.of(""), status().isNotFound(), "Indirizzo non trovato.");
  }

  @Test
  public void descrizioneNonValida() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", "1", "true",
        Optional.of(("a").repeat(320)), status().isBadRequest(), "Descrizione supporto troppo lunga.");
  }

  @Test
  public void descrizioneNonPresente() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", "1", "true",
        Optional.of(""), status().isBadRequest(), "Descrizione supporto non inserita.");
  }

  @Test
  public void ordineSupportoValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", "1", "true",
        Optional.of("Consegna al primo piano"), status().is3xxRedirection(), null);
  }

  @Test
  public void ordineValido() throws Exception {
    testCreazioneOrdine("Luigi Rossi", "5032123166322313", "02/26", "123", "1", "false",
        Optional.of(""), status().is3xxRedirection(), null);
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private void testCreazioneOrdine(String nomeTitolare, String numeroCarta, String dataScadenza,
                                   String cvv, String Indirizzo, String isSupporto,
                                   Optional<String> descrizioneSupporto,
                                   ResultMatcher resultmatcher,
                                   String expectedMessage)
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
    Long idIndirizzo = Long.valueOf(Indirizzo);
    carrello.put(prodotto1.getId(), 10);
    carrello.put(prodotto2.getId(), 50);
    indirizzo.setId(idIndirizzo);
    ordineForm =
        new OrdineForm(numeroCarta, dataScadenza, cvv, nomeTitolare, idIndirizzo, Boolean.valueOf(isSupporto),
            true, descrizioneSupporto.get());
    when(sessionCliente.getCliente()).thenReturn(Optional.of(cliente));
    when(sessionCarrello.getCarrello()).thenReturn(carrello);
    when(indirizzoDao.findIndirizzoById(idIndirizzo)).thenReturn(
        idIndirizzo > 0 && idIndirizzo != 7355608 ? Optional.of(indirizzo) : Optional.empty());
    when(prodottoDao.findProdottoById(prodotto1.getId())).thenReturn(Optional.of(prodotto1));
    when(prodottoDao.findProdottoById(prodotto2.getId())).thenReturn(Optional.of(prodotto2));

    mockMvc.perform(post("/ordina")
        .param("nomeTitolare", ordineForm.getNomeTitolare())
        .param("numeroCarta", ordineForm.getNumeroCarta())
        .param("dataScadenza", ordineForm.getDataScadenza())
        .param("cvv", ordineForm.getCvv())
        .param("indirizzo", ordineForm.getIndirizzo() == null ? null : ordineForm.getIndirizzo().toString())
        .param("isSupporto",
            ordineForm.getIsSupporto() == null ? null : ordineForm.getIsSupporto().toString())
        .param("isRitiro", "false")
        .param("descrizioneSupporto", descrizioneSupporto.orElse(""))
    ).andExpect(resultmatcher)
            .andExpect(result -> {
              if (expectedMessage != null) {
                String errorMessage = result.getResponse().getErrorMessage();
                assertTrue(errorMessage.contains(expectedMessage),
                        "La risposta non contiene il messaggio atteso: " + expectedMessage);
              }
            });
  }
}
