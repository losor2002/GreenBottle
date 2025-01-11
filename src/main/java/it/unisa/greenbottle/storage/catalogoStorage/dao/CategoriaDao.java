package it.unisa.greenbottle.storage.catalogoStorage.dao;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaDao extends JpaRepository<Categoria, Long> {

  List<Categoria> getCategoriaByNome(String nome);
}
