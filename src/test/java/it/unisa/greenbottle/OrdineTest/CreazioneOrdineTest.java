package it.unisa.greenbottle.OrdineTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.dao.IndirizzoDao;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
public class CreazioneOrdineTest {

  private static final String ordineView = "OrdineView/Ordine";

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private SessionCliente sessionCliente;

  @MockitoBean
  private OrdineDao ordineDao;

  @MockitoBean
  private IndirizzoDao indirizzoDao;

  @MockitoBean
  private ClienteDao clienteDao;

  @MockitoBean
  private SessionCarrello sessionCarrello;

  @Test
  public void quantitaNonValida() throws Exception {

  }


  private void test(
      String Nome_titolare,
      String Numero_carta,
      String Data_scadenza,
      String CVV,
      Long id_Indirizzo,
      boolean isSupporto,
      Optional<String> Descrizione_Supporto,
      ResultMatcher resultMatcher
  ) throws Exception {

    Cliente cliente = new Cliente();
    Indirizzo indirizzo = new Indirizzo();
    Map<Long, Integer> carrello = new HashMap<>();
    indirizzo.setId(id_Indirizzo);


    when(sessionCliente.getCliente()).thenReturn(Optional.of(cliente));
    when(sessionCarrello.getCarrello()).thenReturn(Optional.of(carrello));
    when(indirizzoDao.findById(id_Indirizzo)).thenReturn(
        id_Indirizzo > 0 ? Optional.of(indirizzo) : Optional.empty());

    mockMvc.perform(multipart("/ordina")
            .param("Nome_titolare", Nome_titolare)
            .param("Numero_carta", Numero_carta)
            .param("Data_scadenza", Data_scadenza)
            .param("CVV", CVV)
            .param("id_Indirizzo", id_Indirizzo.toString())
            .param("flagSupporto", String.valueOf(isSupporto))
            .param("Descrizione_Supporto", Descrizione_Supporto.orElse(""))
        )
        .andExpect(resultMatcher)
        .andDo(result -> {
          String viewName = result.getModelAndView().getViewName();

          if ("creazioneOrdineView/error".equals(viewName)) {
            verify(ordineDao, never()).save(Mockito.any(Ordine.class));
          } else if ("creazioneOrdineView/success".equals(viewName)) {
            ArgumentCaptor<Ordine> ordineCaptor = ArgumentCaptor.forClass(Ordine.class);
            verify(ordineDao).save(ordineCaptor.capture());

            Ordine savedOrdine = ordineCaptor.getValue();
            assertNotNull("Ordine should be saved", savedOrdine);

            String expectedCarta = Nome_titolare + " " + Numero_carta + " " + CVV;
            assertEquals("Carta field mismatch", expectedCarta, savedOrdine.getCarta());

            assertEquals("Indirizzo mismatch", indirizzo, savedOrdine.getIndirizzo());
            assertEquals("Support flag mismatch", isSupporto, savedOrdine.isSupporto());

            if (isSupporto) {
              assertNotNull("Descrizione_Supporto should be present", savedOrdine.getDescrizione());
              assertEquals("Descrizione_Supporto mismatch", Descrizione_Supporto.orElse(null),
                  savedOrdine.getDescrizione());
            } else {
              assertNull("Descrizione_Supporto should not be present",
                  savedOrdine.getDescrizione());
            }
          }
        });
  }

}

