package it.unisa.greenbottle.storage.catalogoStorage.dao;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Recensione;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecensioneDao extends JpaRepository<Recensione, Long> {

  List<Recensione> findRecensioneById(Long id);
}
