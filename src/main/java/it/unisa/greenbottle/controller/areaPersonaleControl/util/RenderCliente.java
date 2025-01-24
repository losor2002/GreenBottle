package it.unisa.greenbottle.controller.areaPersonaleControl.util;

import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe per la gestione del caricamento delle immagini dei clienti.
 */
@RestController
public class RenderCliente {

  @Autowired
  private ClienteDao clienteDao;

  /**
   * Metodo per ottenere l'immagine di un cliente.
   * Se l'immagine non è presente, viene restituita l'immagine di default.
   *
   * @param id L'id del cliente di cui si vuole ottenere l'immagine.
   * @return L'immagine del cliente come array di byte.
   */
  @GetMapping("/userImg")
  public ResponseEntity<byte[]> getImage(@RequestParam Long id) {
    Optional<Cliente> optCliente = clienteDao.findClienteById(id);
    if (optCliente.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Cliente cliente = optCliente.get();
    byte[] image = cliente.getImg() != null ? cliente.getImg() : loadDefaultImage();

    if (image == null) {
      // Se non è possibile caricare l'immagine di default, restituisci un errore 500
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "image/jpeg");
    headers.add("Content-Length", String.valueOf(image.length));
    return new ResponseEntity<>(image, headers, HttpStatus.OK);
  }

  /**
   * Metodo per caricare l'immagine di default.
   *
   * @return L'immagine di default come array di byte o null in caso di errore.
   */
  private byte[] loadDefaultImage() {
    try {
      // Percorso dell'immagine di default
      Path defaultImagePath = Paths.get("src/main/resources/static/img/defaultImgCliente.png");
      return Files.readAllBytes(defaultImagePath);
    } catch (IOException e) {
      // Log dell'errore per il debug
      e.printStackTrace();
      return null;
    }
  }
}
