package it.unisa.greenbottle.storage.catalogoStorage.dao;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Recensione;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecensioneDao extends JpaRepository<Recensione, Long> {

  Optional<Recensione> findRecensioneById(Long idRecensione);
}
