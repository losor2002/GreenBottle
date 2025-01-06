package it.unisa.greenbottle.storage.accessoStorage.dao;

import it.unisa.greenbottle.storage.accessoStorage.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Admin, Long> {
  Optional<Admin> findAdminByEmail(String email);
}
