package backend.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record WarningDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer warningId,

        @NotBlank(message = "Temat nie moze byc pusty")
        String issueTopic,

        @NotBlank(message = "Zmiana powinna dotyczyć konkretnych produktów")
        String affectedProducts,

        @NotBlank(message = "Prosimy o opis co ma zostać zmienione by te informacje miały sens")
        String description,

        String expectations,

        @NotNull(message = "Nie podano identyfikatora zakupu")
        Integer orderId,

        @NotNull(message = "Nie podano identyfikatora użytkownika składającego zamówienie")
        Integer userId
)
{}
