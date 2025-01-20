package it.unisa.greenbottle.DataJPATests;


import it.unisa.greenbottle.storage.accessoStorage.dao.AdminDao;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Admin;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UtentiTest {

  @Autowired
  private ClienteDao clienteDao;


  @Autowired
  private AdminDao adminDao;


  Cliente clienteTest =
      new Cliente("mario.rossi@gmail.com", "asdfASDF1234!", "Mario", "Rossi", 0, 0, null, null);

  Admin adminTest = new Admin("admin1@greenbottle.it", "asdfASDF1234!");


  @Test
  public void createCliente() {
    clienteDao.save(clienteTest);
    System.out.println("cliente test: " + clienteTest);

    Optional<Cliente> c2 = clienteDao.findClienteByEmail("mario.rossi@gmail.com");

    System.out.println(c2.isPresent() ? "cliente2" + c2.get() : "Not found");
    assert clienteTest.equals(c2.get());
  }

  @Test
  public void createAdmin() {
    adminDao.save(adminTest);
    System.out.println(adminTest.toString());

    Optional<Admin> a2 = adminDao.findAdminByEmail("admin1@greenbottle.it");

    System.out.println(a2.isPresent() ? a2.get().toString() : "Not found");
    assert adminTest.equals(a2.get());
  }

}
