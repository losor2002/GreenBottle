package it.unisa.greenbottle.AbbonamentoTest;

import it.unisa.greenbottle.controller.abbonamentoControl.form.AbbonamentoForm;
import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.AbbonamentoDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.xml.crypto.Data;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SottoscriviAbbonamentoTest {

    @MockitoBean
    private ClienteDao clienteDao;

    @MockitoBean
    private SessionCliente sessionCliente;

    @MockitoBean
    private AbbonamentoForm abbonamentoForm;

    @MockitoBean
    private AbbonamentoDao abbonamentoDao;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        Cliente cliente = new Cliente();
        when(sessionCliente.getCliente()).thenReturn(Optional.of(cliente));

        Abbonamento abbonamento = new Abbonamento(
                Abbonamento.TipoAbbonamento.BRONZE,
                Abbonamento.RinnovoAbbonamento.SEMESTRALE,
                Abbonamento.FrequenzaAbbonamento.GIORNALIERO,
                120.50f
        );

    }


    @Test
    public void nomeNonValido() throws Exception {
        testSottoscriviAbbonamento("C32", "0000000000000000", "99/99", "00C", status().isBadRequest());
    }

    @Test
    public void numeroCartaNonValido() throws Exception {
        testSottoscriviAbbonamento("Giancarlo Filippi", "0000000000000000", "99/99", "00C", status().isBadRequest());
    }

    @Test
    public void dataNonValido() throws Exception {
        testSottoscriviAbbonamento("Giancarlo Filippi", "5267893664829376", "99/99", "00C", status().isBadRequest());
    }

    @Test
    public void CVVNonValido() throws Exception {
        testSottoscriviAbbonamento("Giancarlo Filippi", "5267893664829376", "11/27", "00C", status().isBadRequest());
    }


    public void testSottoscriviAbbonamento(String Nome_titolare, String Numero_carta, String Data_scadenza, String CVV, ResultMatcher resultMatcher) throws Exception {
        abbonamentoForm = new AbbonamentoForm(1L, Numero_carta, Data_scadenza, Nome_titolare, CVV);

        mockMvc.perform(post("/abbonamento")
                .param("id", "1")
                .param("Numero carta", Numero_carta)
                .param("Data scadenza", Data_scadenza)
                .param("Nome titolare", Nome_titolare)
                .param("CVV", CVV))
            .andExpect(resultMatcher);
    }


}
