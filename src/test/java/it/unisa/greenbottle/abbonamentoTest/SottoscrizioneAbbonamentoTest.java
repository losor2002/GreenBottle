package it.unisa.greenbottle.abbonamentoTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unisa.greenbottle.controller.abbonamentoControl.form.AbbonamentoForm;
import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.AbbonamentoDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * Test per la sottoscrizione degli abbonamenti.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SottoscrizioneAbbonamentoTest {

  @MockitoBean
  private SessionCliente sessionCliente;

  @MockitoBean
  private AbbonamentoForm abbonamentoForm;

  @MockitoBean
  private ClienteDao clienteDao;

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private AbbonamentoDao abbonamentoDao;

  /**
   * Setup per il test.
   */
  @BeforeEach
  public void setUp() {
    Cliente cliente = new Cliente();
    when(sessionCliente.getCliente()).thenReturn(Optional.of(cliente));
    when(clienteDao.findClienteById(1L)).thenReturn(Optional.of(cliente));

    Abbonamento abbonamento = new Abbonamento(
        Abbonamento.TipoAbbonamento.BRONZE,
        Abbonamento.RinnovoAbbonamento.SEMESTRALE,
        Abbonamento.FrequenzaAbbonamento.GIORNALIERO,
        120.50f
    );
    abbonamento.setId(1L);
  }


  @Test
  public void nomeNonValido() throws Exception {
    testSottoscriviAbbonamento("C32", "5267893664829376", "11/27", "337",
        status().isBadRequest(), "Nome del titolare della carta non valido.");
  }

  @Test
  public void numeroCartaNonValido() throws Exception {
    testSottoscriviAbbonamento("Giancarlo Filippi", "00000000000000000", "11/27", "337",
        status().isBadRequest(), "Numero di carta non valido.");
  }

  @Test
  public void dataNonValido() throws Exception {
    testSottoscriviAbbonamento("Giancarlo Filippi", "5267893664829376", "99/99", "337",
        status().isBadRequest(), "Data di scadenza della carta non valida.");
  }

  @Test
  public void cvvNonValido() throws Exception {
    testSottoscriviAbbonamento("Giancarlo Filippi", "5267893664829376", "11/27", "00C",
        status().isBadRequest(), "CVV non valido.");
  }

  @Test
  public void abbonamentoSottoscritto() throws Exception {
    Abbonamento abbonamento = new Abbonamento();
    when(abbonamentoDao.findAbbonamentoById(1L)).thenReturn(Optional.of(abbonamento));
    testSottoscriviAbbonamento("Giancarlo Filippi", "5267893664829376", "11/27", "337",
        status().is3xxRedirection(), null);
  }


  /**
   * Test per la sottoscrizione degli abbonamenti.
   *
   * @param nomeTitolare    Stringa con il nome del titolare
   * @param numeroCarta     Stringa con il numero di carta
   * @param dataScadenza    Stringa con la data di scadenza
   * @param cvv             Stringa con il cvv della carta di credito
   * @param resultMatcher   resultMatcher
   * @param expectedMessage messaggio atteso
   * @throws Exception eccezione
   */
  public void testSottoscriviAbbonamento(String nomeTitolare, String numeroCarta,
                                         String dataScadenza, String cvv,
                                         ResultMatcher resultMatcher, String expectedMessage)
      throws Exception {
    abbonamentoForm = new AbbonamentoForm(1L, numeroCarta, dataScadenza, nomeTitolare, cvv);

    mockMvc.perform(post("/abbonamento")
            .param("id", "1")
            .param("numeroCarta", numeroCarta)
            .param("dataScadenza", dataScadenza)
            .param("nomeTitolare", nomeTitolare)
            .param("cvv", cvv))
        .andExpect(resultMatcher)
        .andExpect(result -> {
          if (expectedMessage != null) {
            String errorMessage = result.getResponse().getErrorMessage();
            assertTrue(errorMessage.contains(expectedMessage),
                "La risposta non contiene il messaggio atteso: " + expectedMessage);
          }
        });
  }
}
