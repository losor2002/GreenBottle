package it.unisa.greenbottle.CatalogoTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
  public void idCategoriaNonValido() {
    assertThatThrownBy(
        () -> testFiltraProdotti("-1", null, null, null, null)
    ).isInstanceOf(IllegalArgumentException.class).hasMessage("id_Categoria non esiste");
  }

  @Test
  public void idCategoriaNonEsiste() {
    when(categoriaDao.existsById(3L)).thenReturn(false);
    assertThatThrownBy(
        () -> testFiltraProdotti("3", null, null, null, null)
    ).isInstanceOf(IllegalArgumentException.class).hasMessage("id_Categoria non esiste");
  }

  @Test
  public void idCategoriaEsiste() throws Exception {
    when(categoriaDao.existsById(3L)).thenReturn(true);
    testFiltraProdotti("3", null, null, null, status().isOk());
  }

  @Test
  public void prezzoMinimoNonValido() {
    assertThatThrownBy(
        () -> testFiltraProdotti(null, "-5", null, null, null)
    ).isInstanceOf(IllegalArgumentException.class).hasMessage("Limite minimo di PrezzoMin violato");
  }

  @Test
  public void prezzoMinimoValido() throws Exception {
    testFiltraProdotti(null, "8.5", null, null, status().isOk());
  }

  @Test
  public void prezzoMassimoMinoreDiPrezzoMinimo() {
    assertThatThrownBy(
        () -> testFiltraProdotti(null, "10", "8.5", null, null)
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("PrezzoMax è minore rispetto a PrezzoMin");
  }

  @Test
  public void prezzoMassimoValido() throws Exception {
    testFiltraProdotti(null, "10", "40.25", null, status().isOk());
  }

  @Test
  public void mediaMinimaNonValida() {
    assertThatThrownBy(
        () -> testFiltraProdotti(null, null, null, "-3", null)
    ).isInstanceOf(IllegalArgumentException.class).hasMessage("Limite di Media violato");
  }

  @Test
  public void mediaMinimaValida() throws Exception {
    testFiltraProdotti(null, null, null, "4", status().isOk());
  }

  private void testFiltraProdotti(String idCategoria, String prezzoMin, String prezzoMax,
                                  String media,
                                  ResultMatcher resultMatcher)
      throws Exception {
    if (idCategoria != null) {
      long id = Long.parseLong(idCategoria);

      if (!categoriaDao.existsById(id) || id <= 0) {
        throw new IllegalArgumentException("id_Categoria non esiste");
      }
    }

    if (prezzoMin != null) {
      float prezzoMinFloat = Float.parseFloat(prezzoMin);

      if (prezzoMinFloat <= 0) {
        throw new IllegalArgumentException("Limite minimo di PrezzoMin violato");
      }

      if (prezzoMax != null) {
        float prezzoMaxFloat = Float.parseFloat(prezzoMax);

        if (prezzoMaxFloat < prezzoMinFloat) {
          throw new IllegalArgumentException("PrezzoMax è minore rispetto a PrezzoMin");
        }
      }
    }

    if (media != null) {
      int mediaInt = Integer.parseInt(media);

      if (mediaInt < 1 || mediaInt > 5) {
        throw new IllegalArgumentException("Limite di Media violato");
      }
    }

    mockMvc.perform(get("/catalogo")
            .param("categoria", idCategoria == null ? "" : idCategoria)
            .param("prezzoMin", prezzoMin == null ? "" : prezzoMin)
            .param("prezzoMax", prezzoMax == null ? "" : prezzoMax)
            .param("votoMedio", media == null ? "" : media))
        .andExpect(resultMatcher);
  }
}
