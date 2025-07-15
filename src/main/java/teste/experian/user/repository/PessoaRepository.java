package teste.experian.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teste.experian.user.entity.Pessoa;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByNome(String nome);

    List<Pessoa> findByCep(String cep);

}

