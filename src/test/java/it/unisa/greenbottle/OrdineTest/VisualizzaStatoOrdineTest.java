package it.unisa.greenbottle.OrdineTest;

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
public class VisualizzaStatoOrdineTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SessionCliente sessionCliente;

  @MockitoBean
  private OrdineDao ordineDao;

  @Test
  public void idNonValido() throws Exception {
    test(-1L, status().isBadRequest());
  }

  @Test
  public void idNonPresente() throws Exception {
    Cliente cliente = new Cliente();
    final String testId = "999";
    Ordine ordine =
        new Ordine(12f, Ordine.StatoSpedizione.ELABORAZIONE, false, "0000-0000-0000-0000",
            false, "", new Timestamp(System.currentTimeMillis()), cliente, null);
    ordine.setId(1L);
    ordineDao.save(ordine);

    when(ordineDao.findOrdineById(1L)).thenReturn(Optional.of(ordine));
    when(sessionCliente.getCliente()).thenReturn(Optional.of(cliente));

    mockMvc.perform(get("/areaPersonale/visualizzaStatoOrdine").param("id", testId))
        .andExpect(status().isNotFound());
  }

  @Test
  public void idValido() throws Exception {
    test(1L, status().isOk());
  }

  private void test(Long idOrdine, ResultMatcher resultMatcher) throws Exception {
    Cliente cliente = new Cliente();
    Ordine ordine =
        new Ordine(12f, Ordine.StatoSpedizione.ELABORAZIONE, false, "0000-0000-0000-0000",
            false, "", new Timestamp(System.currentTimeMillis()), cliente, null);
    ordine.setId(1L);

    when(ordineDao.findOrdineById(any())).thenReturn(Optional.of(ordine));
    when(sessionCliente.getCliente()).thenReturn(Optional.of(cliente));

    mockMvc.perform(get("/areaPersonale/visualizzaStatoOrdine")
            .param("id", idOrdine.toString()))
        .andExpect(resultMatcher);

  }

}
