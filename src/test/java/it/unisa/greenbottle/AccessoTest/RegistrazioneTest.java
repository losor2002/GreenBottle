package it.unisa.greenbottle.AccessoTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unisa.greenbottle.controller.accessoControl.form.RegistrazioneForm;
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
  private RegistrazioneForm registrazioneForm;

  @Test
  public void formatoNomeErrato() throws Exception {
    testRegistrazione("Gian@!@Carlo", "Toron@!@to", ("a").repeat(320), "ciao",
        status().isBadRequest());
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
  public void formatoPasswordErrato() throws Exception {
    testRegistrazione("Giancarlo", "Toronto", "GiancarloToronto1966@gmail.com", "ciao",
        status().isBadRequest());
  }

  @Test
  public void registrazioneEffettuata()
      throws Exception {
    test(NomeValido, CognomeValido, EmailValida, PasswordValida, status().is3xxRedirection());
  }

  private void test(String nome, String cognome, String email, String password,
                    ResultMatcher resultMatcher) throws Exception {

    registrazioneForm = new RegistrazioneForm(nome, cognome, email, password);

    mockMvc.perform(post("/registrazione")
        .param("nome", registrazioneForm.getNome())
        .param("cognome", registrazioneForm.getCognome())
        .param("email", registrazioneForm.getEmail())
        .param("password", registrazioneForm.getPassword())
    ).andExpect(resultMatcher);
  }
}
