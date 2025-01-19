package it.unisa.greenbottle.AreaPersonaleTest;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VisualizzaStoricoOrdiniTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessionCliente sessionCliente;

    @MockitoBean
    private OrdineDao ordineDao;

    @MockitoBean
    private ClienteDao clienteDao;

    @Test
    public void startDateNonRispettaIlFormato() throws Exception {
        test("2024/01/12", "2025/01/12", status().isBadRequest());
    }

    @Test
    public void endDateNonRispettaIlFormato() throws Exception {
        test("2024-01-12", "2025/01/12", status().isBadRequest());
    }

    @Test
    public void dateRispettaIlFormato() throws Exception {
        test("2024-01-12", "2025-01-12", status().isOk());
    }

    private void test(String startDate, String endDate, ResultMatcher resultMatcher) throws Exception {
        Cliente cliente = new Cliente();
        Ordine ordine = new Ordine(12f, Ordine.StatoSpedizione.ELABORAZIONE, false, "0000-0000-0000-0000",
                                    false, "", new Timestamp(System.currentTimeMillis()), cliente, null);

        when(ordineDao.findById(any())).thenReturn(Optional.of(ordine));
        when(sessionCliente.getCliente()).thenReturn(Optional.of(cliente));

        mockMvc.perform(post("/StoricoOrdini").param("startDate", startDate).param("endDate", endDate)).andExpect(resultMatcher);
    }
}
