package it.unisa.greenbottle.storage.catalogoStorage.dao;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Categoria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dei dati delle categorie nel database.
 */
public interface CategoriaDao extends JpaRepository<Categoria, Long> {
  Optional<Categoria> findCategoriaById(Long id);
}
