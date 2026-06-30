package backend.shop.dto;

import java.util.List;

public record ActiveAdminsDTO(
        List<UserDTO> activeAdmins
)
{}
