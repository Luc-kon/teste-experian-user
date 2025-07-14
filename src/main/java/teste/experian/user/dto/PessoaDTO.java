package teste.experian.user.dto;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Idade é obrigatória")
    @Min(value = 0, message = "Idade mínima é 0")
    private Integer idade;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(min = 10, max = 11, message = "Telefone deve ter entre 10 e 11 dígitos")
    private String telefone;

    @NotNull(message = "Score é obrigatório")
    @Min(value = 0, message = "Score mínimo é 0")
    @Max(value = 1000, message = "Score máximo é 1000")
    private Integer score;

    private String scoreDescricao;

    private List<EnderecoDTO> enderecos;
}
