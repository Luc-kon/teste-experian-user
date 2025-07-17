package teste.experian.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teste.experian.user.entity.Pessoa;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByNome(String nome);

    @Query("""
        SELECT DISTINCT p FROM Pessoa p
        LEFT JOIN Endereco e ON p = e.pessoa
        WHERE p.ativo = true
        AND (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND (:idade IS NULL OR p.idade = :idade)
        AND (:cep IS NULL OR e.cep = :cep)
    """)
    Page<Pessoa> buscarComFiltros(
            @Param("nome") String nome,
            @Param("idade") Integer idade,
            @Param("cep") String cep,
            Pageable pageable
    );
}