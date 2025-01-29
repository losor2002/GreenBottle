package it.unisa.greenbottle.catalogoTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unisa.greenbottle.controller.catalogoControl.form.FiltroForm;
import it.unisa.greenbottle.storage.catalogoStorage.dao.CategoriaDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * Testa la funzionalità di filtraggio dei prodotti.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class FiltraProdottiTest {

  @MockitoBean
  private CategoriaDao categoriaDao;

  @MockitoBean
  private FiltroForm filtroForm;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void idCategoriaNonValido() throws Exception {
    testFiltraProdotti("-1", null, null, null, status().isBadRequest(),
        "IdCategoria deve essere maggiore o uguale a 1.");
  }

  @Test
  public void idCategoriaNonEsiste() throws Exception {
    when(categoriaDao.existsById(3L)).thenReturn(false);
    testFiltraProdotti("10", null, null, null, status().isNotFound(), "Categoria non presente.");
  }

  @Test
  public void idCategoriaEsiste() throws Exception {
    when(categoriaDao.existsById(3L)).thenReturn(true);

    Categoria categoria = new Categoria();
    categoria.setId(3L);
    when(categoriaDao.findCategoriaById(3L)).thenReturn(Optional.of(categoria));

    testFiltraProdotti("3", null, null, null, status().isOk(), null);
  }

  @Test
  public void prezzoMinimoNonValido() throws Exception {
    testFiltraProdotti(null, "-5", null, null, status().isBadRequest(),
        "Prezzo minimo deve essere maggiore o uguale a 1.");
  }

  @Test
  public void prezzoMinimoValido() throws Exception {
    testFiltraProdotti(null, "8.5", null, null, status().isOk(), null);
  }

  @Test
  public void prezzoMassimoMinoreDiPrezzoMinimo() throws Exception {
    testFiltraProdotti(null, "10", "8.5", null, status().isBadRequest(),
        "Prezzo minimo non può essere maggiore del prezzo massimo.");
  }

  @Test
  public void prezzoMassimoValido() throws Exception {
    testFiltraProdotti(null, "10", "40.25", null, status().isOk(), null);
  }

  @Test
  public void mediaNonValida() throws Exception {
    testFiltraProdotti(null, null, null, "-3", status().isBadRequest(),
        "Media deve essere almeno 1.0");
  }

  @Test
  public void mediaValida() throws Exception {
    testFiltraProdotti(null, null, null, "4", status().isOk(), null);
  }

  private void testFiltraProdotti(String idCategoria, String prezzoMin, String prezzoMax,
                                  String media,
                                  ResultMatcher resultMatcher,
                                  String expectedMessage)
      throws Exception {

    filtroForm = new FiltroForm(
        idCategoria == null ? null : Long.valueOf(idCategoria),
        prezzoMin == null ? null : Float.valueOf(prezzoMin),
        prezzoMax == null ? null : Float.valueOf(prezzoMax),
        media == null ? null : Float.valueOf(media));

    mockMvc.perform(get("/catalogo")
            .param("idCategoria",
                filtroForm.getIdCategoria() == null ? null : filtroForm.getIdCategoria().toString())
            .param("prezzoMin",
                filtroForm.getPrezzoMin() == null ? null : filtroForm.getPrezzoMin().toString())
            .param("prezzoMax",
                filtroForm.getPrezzoMax() == null ? null : filtroForm.getPrezzoMax().toString())
            .param("media",
                filtroForm.getMedia() == null ? null : filtroForm.getMedia().toString()))
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
