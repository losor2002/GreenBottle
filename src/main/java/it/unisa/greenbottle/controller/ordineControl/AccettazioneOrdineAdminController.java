package it.unisa.greenbottle.controller.ordineControl;


import it.unisa.greenbottle.controller.accessoControl.util.SessionAdmin;
import it.unisa.greenbottle.storage.ordineStorage.dao.ComposizioneDao;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/accettazioneOrdine")
public class AccettazioneOrdineAdminController {

  @Autowired
  private ComposizioneDao composizioneDao;

  @Autowired
  private OrdineDao ordineDao;

  @Autowired
  private SessionAdmin sessionAdmin;

  @PostMapping
  public ResponseEntity<?> post(@RequestParam("ordineId") String id,
                                @RequestParam("newState") String statoSpedizione) {
    Long veroId = Long.valueOf(id);
    if (veroId <= 0) { // formato errato dell'id
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id non valido.");
    }

    Optional<Ordine> optOrdine = ordineDao.findOrdineById(veroId);
    if (optOrdine.isEmpty()) {
      // Risponde con un errore se l'ordine non esiste
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordine non presente.");
    }

    Ordine ordine = optOrdine.get();
    ordine.setStato(Ordine.StatoSpedizione.valueOf(statoSpedizione));
    ordine.setAdmin(sessionAdmin.getAdmin().get());
    ordineDao.save(ordine);

    // Risponde con un messaggio di successo
    return ResponseEntity.status(HttpStatus.OK)
        .body("Stato dell'ordine aggiornato a: " + statoSpedizione);
  }
}
