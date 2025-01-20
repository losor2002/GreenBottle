package it.unisa.greenbottle.CatalogoTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.unisa.greenbottle.storage.catalogoStorage.dao.CategoriaDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
public class FiltraProdottiTest {

  @MockBean
  private CategoriaDao categoriaDao;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void idCategoriaNonValido() throws Exception {
    testFiltraProdotti("-1", null, null, null, status().isBadRequest());
  }

  @Test
  public void idCategoriaNonEsiste() throws Exception {
    when(categoriaDao.existsById(3L)).thenReturn(false);
    testFiltraProdotti("3", null, null, null, status().isBadRequest());
  }

  @Test
  public void idCategoriaEsiste() throws Exception {
    when(categoriaDao.existsById(3L)).thenReturn(true);
    testFiltraProdotti("3", null, null, null, status().isOk());
  }

  @Test
  public void prezzoMinimoNonValido() throws Exception {
    testFiltraProdotti(null, "-5", null, null, status().isBadRequest());
  }

  @Test
  public void prezzoMinimoValido() throws Exception {
    testFiltraProdotti(null, "8.5", null, null, status().isOk());
  }

  @Test
  public void prezzoMassimoMinoreDiPrezzoMinimo() throws Exception {
    testFiltraProdotti(null, "10", "8.5", null, status().isBadRequest());
  }

  @Test
  public void prezzoMassimoValido() throws Exception {
    testFiltraProdotti(null, "10", "40.25", null, status().isOk());
  }

  @Test
  public void mediaMinimaNonValida() throws Exception {
    testFiltraProdotti(null, null, null, "-3", status().isBadRequest());
  }

  @Test
  public void mediaMinimaValida() throws Exception {
    testFiltraProdotti(null, null, null, "4", status().isOk());
  }

  private void testFiltraProdotti(String idCategoria, String prezzoMin, String prezzoMax,
                                  String media,
                                  ResultMatcher resultMatcher)
      throws Exception {

    mockMvc.perform(get("/catalogo")
            .param("categoria", idCategoria == null ? "" : idCategoria)
            .param("prezzoMin", prezzoMin == null ? "" : prezzoMin)
            .param("prezzoMax", prezzoMax == null ? "" : prezzoMax)
            .param("votoMedio", media == null ? "" : media))
        .andExpect(resultMatcher);
  }
}
