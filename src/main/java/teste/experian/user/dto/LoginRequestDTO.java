package teste.experian.user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank()
    @Size(min = 4, max = 16)
    private String username;

    @NotBlank()
    @Size(min = 4, max = 16)
    private String password;
}
