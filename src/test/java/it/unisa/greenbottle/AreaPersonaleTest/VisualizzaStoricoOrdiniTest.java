package it.unisa.greenbottle.AreaPersonaleTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.sql.Timestamp;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
public class VisualizzaStoricoOrdiniTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SessionCliente sessionCliente;

  @MockitoBean
  private OrdineDao ordineDao;


  @Test
  public void startDateNonRispettaIlFormato() throws Exception {
    testVisualizzaStoricoOrdini("2024/01/01", "2025-01-12", status().isBadRequest(), "Data di inizio non valida.");
  }

  @Test
  public void endDateNonRispettaIlFormato() throws Exception {
    testVisualizzaStoricoOrdini("2024-01-12", "2025/01/12", status().isBadRequest(), "Data di fine non valida.");
  }

  @Test
  public void startDateDopoEndDate() throws Exception {
    testVisualizzaStoricoOrdini("2024-01-12", "2024-01-11", status().isBadRequest(), "Data iniziale successiva alla data finale.");
  }

  @Test
  public void dateRispettaIlFormato() throws Exception {
    testVisualizzaStoricoOrdini("2024-01-12", "2025-01-12", status().isOk(), null);
  }

  private void testVisualizzaStoricoOrdini(String startDate, String endDate, ResultMatcher resultMatcher, String expectedMessage)
      throws Exception {
    Cliente cliente = new Cliente();
    Ordine ordine =
        new Ordine(12f, Ordine.StatoSpedizione.ELABORAZIONE, false, "0000-0000-0000-0000",
            false, "", new Timestamp(System.currentTimeMillis()), cliente, null);

    when(ordineDao.findOrdineById(any())).thenReturn(Optional.of(ordine));
    when(sessionCliente.getCliente()).thenReturn(Optional.of(cliente));

    mockMvc.perform(get("/areaPersonale/visualizzaStoricoOrdini").param("startDate", startDate)
        .param("endDate", endDate)).andExpect(resultMatcher).andExpect(result -> {
      if (expectedMessage != null) {
        String errorMessage = result.getResponse().getErrorMessage();
        assertTrue(errorMessage.contains(expectedMessage),
                "La risposta non contiene il messaggio atteso: " + expectedMessage);
      }
    });
  }
}
