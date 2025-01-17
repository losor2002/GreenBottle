package it.unisa.greenbottle.controller.abbonamentoControl.util;

import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Disposizione;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class AbbonamentoWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Abbonamento a;
    private List<Disposizione> d;

    public AbbonamentoWrapper(Abbonamento a, List<Disposizione> d) {
        this.a = a;
        this.d = d;
    }

    public Abbonamento getAbbonamento() { return a; }

    public List<Disposizione> getDisposizione() { return d;}

    public Long getId() { return a.getId(); }

    public Abbonamento.TipoAbbonamento getTipo() { return a.getTipo();}

    public Abbonamento.FrequenzaAbbonamento getFrequenza() { return a.getFrequenza();}

    public Abbonamento.RinnovoAbbonamento getRinnovo() { return a.getRinnovo();}

    public HashMap<Prodotto, Integer> getProdottiAbbonamento() {
        HashMap<Prodotto, Integer> mappa = new HashMap<>();
        for (Disposizione disposizione : d) {
            Prodotto chiave = disposizione.getProdotto();
            Integer valore = disposizione.getQuantita();
            mappa.put(chiave, valore);
        }
        return mappa;
    }

    @Override
    public String toString() {
        return "AbbonamentoWrapper{"
        + "id=" + a.getId()
        + ", tipo=" + a.getTipo()
        + ", frequenza=" + a.getFrequenza()
        + ", rinnovo=" + a.getRinnovo()
        + ", prodottiAbbonamento=" + getProdottiAbbonamento()
        + '}';

    }

}
