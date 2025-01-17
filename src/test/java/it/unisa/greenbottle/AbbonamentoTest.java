package it.unisa.greenbottle;

import it.unisa.greenbottle.controller.catalogoControl.form.FiltroForm;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.AbbonamentoDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.DisposizioneDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Disposizione;
import it.unisa.greenbottle.storage.catalogoStorage.dao.CategoriaDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AbbonamentoTest {
    @Autowired
    private AbbonamentoDao abbonamentoDao;
    @Autowired
    private DisposizioneDao disposizioneDao;
    @Autowired
    private ProdottoDao prodottoDao;
    @Autowired
    private CategoriaDao categoriaDao;

    @Test
    public void abbonamentiTier() {
        Abbonamento abbonamento1 = new Abbonamento(Abbonamento.TipoAbbonamento.BRONZE, Abbonamento.RinnovoAbbonamento.SEMESTRALE, Abbonamento.FrequenzaAbbonamento.MENSILE);
        Abbonamento abbonamento2 = new Abbonamento(Abbonamento.TipoAbbonamento.SILVER, Abbonamento.RinnovoAbbonamento.BIMESTRALE, Abbonamento.FrequenzaAbbonamento.SETTIMANALE);
        Abbonamento abbonamento3 = new Abbonamento(Abbonamento.TipoAbbonamento.GOLD, Abbonamento.RinnovoAbbonamento.MENSILE, Abbonamento.FrequenzaAbbonamento.GIORNALIERO);
        Categoria categoriaDefault = new Categoria("Bevande gassate");
        categoriaDao.save(categoriaDefault);
        Prodotto prodotto1 = new Prodotto("Fanta", "Bevanda", null, 2.5f, 100, null);
        Prodotto prodotto2 = new Prodotto("Coca Cola", "Bevanda", null, 1.5f, 100, null);
        Prodotto prodotto3 = new Prodotto("Sprite", "Bevanda", null, 2.0f, 100, categoriaDefault);
        prodottoDao.save(prodotto1);
        prodottoDao.save(prodotto2);
        prodottoDao.save(prodotto3);

        FiltroForm filterForm = new FiltroForm(null, categoriaDefault.getNome(), null, null, null);
        Specification<Prodotto> spec = Specification.where(null);

        if (filterForm.getNome() != null && !filterForm.getNome().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("nome"), "%" + filterForm.getNome() + "%"));
        }
        if (filterForm.getCategoria() != null && !filterForm.getCategoria().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("categoria").get("nome"), filterForm.getCategoria()));
        }
        if (filterForm.getPrezzoMin() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("prezzo"), filterForm.getPrezzoMin()));
        }
        if (filterForm.getPrezzoMax() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("prezzo"), filterForm.getPrezzoMax()));
        }
        if (filterForm.getVoto() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("votoMedio"), filterForm.getVoto()));
        }

        List<Prodotto> recuperato = prodottoDao.findAll(spec);
        System.out.println(recuperato);
    }
}
