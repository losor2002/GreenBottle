package it.unisa.greenbottle.storage.catalogoStorage.dao;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdottoDao extends JpaRepository<Prodotto, Long> {

  List<Prodotto> findProdottoByCategoria(Categoria categoria);

  List<Prodotto> findAll(Specification<Prodotto> spec);

  Optional<Prodotto> findProdottoById(Long idProdotto);
}

