package teste.experian.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {

    private Long id;
    private String nome;
    private Integer idade;
    private String telefone;
    private Integer score;
    private String scoreDescricao;
    private List<EnderecoDTO> enderecos;
}