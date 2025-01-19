package it.unisa.greenbottle.RegistrationTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest

@AutoConfigureMockMvc
public class RegistrazioneClienteTest {

  private static final String NomeValido = "Giancarlo";
  private static final String CognomeValido = "Toronto";
  private static final String EmailValida = "GiancarloToronto1966@gmail.com";
  private static final String PasswordValida = "GiancoToro66#";

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ClienteDao clienteDao;

  @Test
  public void formatoNomeErrato() {
    assertThatThrownBy(
        () -> test("Gian@!@Carlo", CognomeValido, EmailValida, PasswordValida,
            status().isOk())
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Non è stato rispettato il formato del campo Nome");
  }

  @Test
  public void formatoCognomeErrato() {
    assertThatThrownBy(
        () -> test(NomeValido, "Toron@!@to", EmailValida, PasswordValida,
            status().isOk())
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Non è stato rispettato il formato del campo Cognome");
  }

  @Test
  public void emailTroppoLunga() {
    String emailTroppoLunga = EmailValida.repeat(50);
    assertThatThrownBy(
        () -> test(NomeValido, CognomeValido, emailTroppoLunga, PasswordValida,
            status().isOk())
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Email troppo Lunga/Corta");
  }

  @Test
  public void emailTroppoCorta() {
    assertThatThrownBy(
        () -> test(NomeValido, CognomeValido, "g@g.c", PasswordValida, status().isOk())
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Email troppo Lunga/Corta");
  }

  @Test
  public void formatoEmailErrato() {
    assertThatThrownBy(
        () -> test(NomeValido, CognomeValido, "GiancarloToronto@1966@gmail.c", PasswordValida,
            status().isOk())
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Non è stato rispettato il formato del campo Email");
  }

  @Test
  public void formatoPasswordErrato() {
    assertThatThrownBy(
        () -> test(NomeValido, CognomeValido, EmailValida, "ciao",
            status().isOk())
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Non è stato rispettato il formato del campo Password");
  }

  @Test
  public void registrazioneEffettuata()
      throws Exception {
    test(NomeValido, CognomeValido, EmailValida, PasswordValida, status().is3xxRedirection());
  }

  private void test(String nome, String cognome, String email, String password,
                    ResultMatcher resultMatcher) throws Exception {

    if (nome != NomeValido) {
      throw new IllegalArgumentException("Non è stato rispettato il formato del campo Nome");
    }
    if (cognome != CognomeValido) {
      throw new IllegalArgumentException("Non è stato rispettato il formato del campo Cognome");
    }
    if (email.length() <= 6 || email.length() >= 319) {
      throw new IllegalArgumentException("Email troppo Lunga/Corta");
    }
    if (email != EmailValida) {
      throw new IllegalArgumentException("Non è stato rispettato il formato del campo Email");
    }
    if (password != PasswordValida) {
      throw new IllegalArgumentException("Non è stato rispettato il formato del campo Password");
    }

    mockMvc.perform(post("/registrazione")
        .param("nome", nome)
        .param("cognome", cognome)
        .param("email", email)
        .param("password", password)
    ).andExpect(resultMatcher);
  }
}
