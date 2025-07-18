package teste.experian.user.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.experian.user.dto.PessoaDTO;
import teste.experian.user.dto.PessoaRequestDTO;
import teste.experian.user.service.PessoaService;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaDTO> criar(@Valid @RequestBody PessoaRequestDTO dto) {
        PessoaDTO novaPessoa = pessoaService.criar(dto);
        return ResponseEntity.ok(novaPessoa);
    }

    @GetMapping
    public ResponseEntity<Page<PessoaDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer idade,
            @RequestParam(required = false) String cep,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(pessoaService.listar(nome, idade, cep, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PessoaRequestDTO dto) {
        PessoaDTO atualizada = pessoaService.atualizar(id, dto);
        return atualizada != null ? ResponseEntity.ok(atualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        return pessoaService.excluir(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}