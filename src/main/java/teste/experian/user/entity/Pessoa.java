package teste.experian.user.entity;

import jakarta.persistence.*;
import lombok.Data;  //loombok gera getters e setters!
import lombok.NoArgsConstructor;  //loombok gera constructor vazio pro JPA!

@Entity
@Table(name = "pessoa")
@Data
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer idade;
    private String telefone;
    private Integer score;
    private boolean ativo = true;


}
