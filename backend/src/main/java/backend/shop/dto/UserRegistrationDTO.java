package backend.shop.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer userId,

        @NotBlank(message = "Pole \"imie\" nie moze byc puste")
        @Size(min = 1, max = 13, message = "Pole \"imie\" nie moze byc dluzsze niz 13 liter")
        String firstName,

        @NotBlank(message = "Pole \"nazwisko\" nie moze byc puste")
        @Size(min = 1, max = 55, message = "Pole \"nazwisko\" nie moze byc dluzsze niz 55 liter")
        String lastName,

        @NotBlank(message = "Pole \"email\" nie moze byc puste")
        @Email
        String email,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "Pole \"haslo\" nie moze byc puste")
        @Size(min = 10, message = "Haslo musi miec przynajmniej 10 znakow")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{10,}$",
                message = "Hasło musi zawierać cyfrę, małą i wielką literę"
        )
        String password
) {}
