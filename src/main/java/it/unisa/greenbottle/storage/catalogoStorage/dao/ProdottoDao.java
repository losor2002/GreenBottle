package it.unisa.greenbottle.storage.catalogoStorage.dao;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdottoDao extends JpaRepository<Prodotto, Long> {

  List<Prodotto> findByCategoria(Categoria categoria);

}

