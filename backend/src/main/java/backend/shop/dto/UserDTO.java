package backend.shop.dto;

import backend.shop.model.DeliveryDetails;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

public record UserDTO(

        @NotNull
        Integer userId,

        @Size(min = 1, max = 13, message = "Pole \"imie\" nie moze byc dluzsze niz 13 liter")
        String firstName,

        @Size(min = 1, max = 55, message = "Pole \"nazwisko\" nie moze byc dluzsze niz 55 liter")
        String lastName,

        @Email
        String email,

        @NotNull
        Set<String> role,

        Instant accountCreationDate,

        boolean isActive,

        DeliveryDetails deliveryDetails
) {
}
