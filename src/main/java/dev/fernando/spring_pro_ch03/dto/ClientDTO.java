package dev.fernando.spring_pro_ch03.dto;

import java.time.LocalDate;

import dev.fernando.spring_pro_ch03.entities.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

public record ClientDTO(
    Long id,
    @NotBlank(message = "Campo \"name\" é de preenchimento obrigatório")
    String name,
    String cpf,
    Double income,
    @PastOrPresent(message = "Data de nascimento não pode ser data futura")
    LocalDate birthDate,
    Integer children
) {
    public ClientDTO(Client entity) {
        this(entity.getId(),
        entity.getName(),
        entity.getCpf(),
        entity.getIncome(),
        entity.getBirthDate(),
        entity.getChildren());
    }
}