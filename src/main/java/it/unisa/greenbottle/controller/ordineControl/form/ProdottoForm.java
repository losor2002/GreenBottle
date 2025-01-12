package it.unisa.greenbottle.controller.ordineControl.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdottoForm {
    
    @Positive(message = "id_Prodotto non valido")
    @NotBlank(message = "id_Prodotto non valido")
    private Long idProdotto;
    @Positive(message = "Quantità non valida.")
    @NotBlank(message = "Quantità non valida.")
    private Integer quantita;
    
    
    public Long getIdProdotto() {
        return idProdotto;
    }
}
