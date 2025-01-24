package it.unisa.greenbottle.controller.accessoControl.util;

import it.unisa.greenbottle.storage.accessoStorage.dao.AdminDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Admin;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Classe SessionAdmin, utilizzata per la gestione della sessione dell'admin.
 */
@Component
@SessionScope
public class SessionAdmin {
  private Long idAdmin;

  @Autowired
  private AdminDao adminDao;

  /**
   * Metodo per settare l'admin.
   *
   * @param admin admin da settare
   */
  public void setAdmin(Admin admin) {
    if (admin == null) {
      this.idAdmin = null;
    } else {
      this.idAdmin = admin.getId();
    }
  }

  /**
   * Metodo per ottenere l'admin.
   *
   * @return admin
   */
  @ModelAttribute("admin")
  public Optional<Admin> getAdmin() {
    if (idAdmin != null) {
      return adminDao.findById(idAdmin);
    }
    return Optional.empty();
  }

  public void emptyAdmin() {
    this.idAdmin = null;
  }
}
