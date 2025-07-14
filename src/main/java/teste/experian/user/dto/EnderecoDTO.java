package teste.experian.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private Long id;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos")
    private String cep;

    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String estado;


}
