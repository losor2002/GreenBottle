package it.unisa.greenbottle.AccessoTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
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
public class RegistrazioneTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ClienteDao clienteDao;

  @Test
  public void formatoNomeErrato() throws Exception {
    testRegistrazione("Gian@!@Carlo", "Toronto", "GiancarloToronto#1966@gmail.com", "ciao",
        status().isOk(), "Nome non rispetta il formato.");
  }

  @Test
  public void formatoCognomeErrato() throws Exception {
    testRegistrazione("Giancarlo", "Toron@!@to", "GiancarloToronto#1966@gmail.com", "ciao",
        status().isOk(), "Cognome non rispetta il formato.");
  }

  @Test
  public void emailTroppoLunga() throws Exception {
    testRegistrazione("Giancarlo", "Toronto", ("a").repeat(320) + "@example.com", "ciao",
        status().isOk(), "Dimensione Email errata.");
  }

  @Test
  public void formatoEmailErrato() throws Exception {
    testRegistrazione("Giancarlo", "Toronto", "GiancarloToronto#1966@gmail.c", "ciao",
        status().isOk(), "Formato Email errato.");
  }

  @Test
  public void formatoPasswordErrato() throws Exception {
    testRegistrazione("Giancarlo", "Toronto", "GiancarloToronto1966@gmail.com", "ciao",
        status().isOk(), "La Password deve contenere");
  }

  @Test
  public void registrazioneEffettuata() throws Exception {
    when(clienteDao.findClienteByEmail("GiancarloToronto1966@example.com")).thenReturn(
        Optional.empty());

    testRegistrazione("Giancarlo", "Toronto", "GiancarloToronto1966@gmail.com", "GiancoToro66!",
        status().is3xxRedirection(), null);
  }

  /**
   * Funzione di supporto per eseguire test sulla registrazione.
   *
   * @param nome            Nome dell'utente
   * @param cognome         Cognome dell'utente
   * @param email           Email dell'utente
   * @param password        Password dell'utente
   * @param expectedStatus  Status HTTP atteso
   * @param expectedMessage Messaggio di errore atteso nella risposta (null se non serve controllare)
   * @throws Exception In caso di errori durante il test
   */
  private void testRegistrazione(String nome, String cognome, String email, String password,
                                 ResultMatcher expectedStatus, String expectedMessage)
      throws Exception {
    mockMvc.perform(post("/registrazione")
            .param("nome", nome)
            .param("cognome", cognome)
            .param("email", email)
            .param("password", password))
        .andExpect(expectedStatus)
        .andExpect(result -> {
          if (expectedMessage != null) {
            String responseContent = result.getResponse().getContentAsString();
            assertTrue(responseContent.contains(expectedMessage),
                "La risposta non contiene il messaggio atteso: " + expectedMessage);
          }
        });
  }
}
