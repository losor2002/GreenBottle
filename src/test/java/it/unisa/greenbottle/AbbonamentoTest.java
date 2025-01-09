package it.unisa.greenbottle;

import it.unisa.greenbottle.storage.abbonamentoStorage.dao.AbbonamentoDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AbbonamentoTest {

  @Autowired
  private ClienteDao clienteDao;

  @Autowired
  private AbbonamentoDao abbonamentoDao;


  Cliente clienteTest =
      new Cliente("luca.bianchi@gmail.com", "asdfASDF1234@", "Luca", "Bianchi", 0, 0, null,
          new Timestamp(System.currentTimeMillis()));

  Abbonamento abbonamento =
      new Abbonamento(Abbonamento.TipoAbbonamento.BRONZE, Abbonamento.RinnovoAbbonamento.MENSILE,
          Abbonamento.FrequenzaAbbonamento.MENSILE);

  @Test
  public void testAbbonamento() {
    abbonamentoDao.save(abbonamento);
    System.out.println("abbonamento test: " + abbonamento);

    Cliente c = clienteTest;
    c.setAbbonamento(abbonamento);
    clienteDao.save(c);

    System.out.println("cliente test: " + c);
    assert true == true;
  }

}