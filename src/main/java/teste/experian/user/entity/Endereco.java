package teste.experian.user.entity;

import jakarta.persistence.*;
import lombok.Data;  //loombok gera getters e setters!
import lombok.NoArgsConstructor;  //loombok gera constructor vazio pro JPA!

@Entity
@Table(name = "endereco")
@Data
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String logradouroNumero;
    private String complemento;

    @ManyToOne
    @JoinColumn(name = "pessoaId")
    private Pessoa pessoa;

}
