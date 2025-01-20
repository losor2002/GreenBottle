package it.unisa.greenbottle.AccessoTest;

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
  public void formatoCognomeErrato() throws Exception {
    testRegistrazione("Giancarlo", "Toron@!@to", ("a").repeat(320), "ciao",
        status().isBadRequest());
  }

  @Test
  public void emailTroppoLunga() throws Exception {
    testRegistrazione("Giancarlo", "Toronto", ("a").repeat(320), "ciao", status().isBadRequest());
  }

  @Test
  public void formatoEmailErrato() throws Exception {
    testRegistrazione("Giancarlo", "Toronto", "GiancarloToronto@1966@gmail.c", "ciao",
        status().isBadRequest());
  }

  @Test
  public void formatoPasswordErrato() throws Exception {
    testRegistrazione("Giancarlo", "Toronto", "GiancarloToronto1966@gmail.com", "ciao",
        status().isBadRequest());
  }

  @Test
  public void registrazioneEffettuata() throws Exception {
    testRegistrazione("Giancarlo", "Toronto", "GiancarloToronto1966@gmail.com", "Giancotoro66!",
        status().isOk());
  }

  private void testRegistrazione(String nome, String cognome, String email, String password,
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
