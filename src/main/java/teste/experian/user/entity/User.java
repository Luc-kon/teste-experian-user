package teste.experian.user.entity;

import jakarta.persistence.*;
import lombok.Data;  //loombok gera getters e setters!
import lombok.NoArgsConstructor;  //loombok gera constructor vazio pro JPA!

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private long tipoUsuario;

}
