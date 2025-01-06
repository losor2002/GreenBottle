package it.unisa.greenbottle.storage.catalogoStorage.dao;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Recensione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecensioneDao extends JpaRepository<Recensione, Long> {

}
