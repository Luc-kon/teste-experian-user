package teste.experian.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.web.client.RestTemplate;
import teste.experian.user.dto.EnderecoDTO;
import teste.experian.user.dto.PessoaDTO;
import teste.experian.user.dto.PessoaRequestDTO;
import teste.experian.user.entity.Pessoa;
import teste.experian.user.repository.EnderecoRepository;
import teste.experian.user.repository.PessoaRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarPessoaComEndereco() {
        PessoaRequestDTO dto = criarPessoaRequestDTO();
        Pessoa pessoaSalva = criarPessoa();
        Pessoa pessoaComId = criarPessoa();
        pessoaComId.setId(1L);

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaComId);
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaComId));
        when(restTemplate.getForObject(anyString(), eq(EnderecoDTO.class)))
                .thenReturn(criarEnderecoViaCep());

        PessoaDTO response = pessoaService.criar(dto);

        assertNotNull(response);
        assertEquals("João", response.getNome());
        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    void testListarPessoas() {
        Pageable pageable = PageRequest.of(0, 10);
        Pessoa pessoa = criarPessoa();
        pessoa.setId(1L);
        Page<Pessoa> page = new PageImpl<>(List.of(pessoa));

        when(pessoaRepository.buscarComFiltros(null, null, null, pageable)).thenReturn(page);
        when(enderecoRepository.findByPessoaId(1L)).thenReturn(List.of());

        Page<PessoaDTO> result = pessoaService.listar(null, null, null, pageable);

        assertEquals(1, result.getTotalElements());
        verify(pessoaRepository).buscarComFiltros(null, null, null, pageable);
    }

    @Test
    void testAtualizarPessoa() {
        PessoaRequestDTO dto = criarPessoaRequestDTO();
        Pessoa pessoaExistente = criarPessoa();
        pessoaExistente.setId(1L);

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaExistente));
        when(pessoaRepository.save(any())).thenReturn(pessoaExistente);
        when(enderecoRepository.findByPessoaId(1L)).thenReturn(List.of());
        when(restTemplate.getForObject(anyString(), eq(EnderecoDTO.class)))
                .thenReturn(criarEnderecoViaCep());

        PessoaDTO result = pessoaService.atualizar(1L, dto);

        assertEquals("João", result.getNome());
        verify(enderecoRepository).deleteAllByPessoaId(1L);
    }

    @Test
    void testExcluirPessoa() {
        Pessoa pessoa = criarPessoa();
        pessoa.setId(1L);

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any())).thenReturn(pessoa);

        boolean result = pessoaService.excluir(1L);

        assertTrue(result);
        verify(pessoaRepository).save(any());
    }

    @Test
    void testExcluirPessoaNaoEncontrada() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = pessoaService.excluir(1L);

        assertFalse(result);
    }

    // ========== Helpers ==========

    private PessoaRequestDTO criarPessoaRequestDTO() {
        PessoaRequestDTO dto = new PessoaRequestDTO();
        dto.setNome("João");
        dto.setIdade(30);
        dto.setTelefone("11999999999");
        dto.setScore(500);

        EnderecoDTO end = new EnderecoDTO();
        end.setCep("01001000");
        end.setNumero("123");
        end.setComplemento("Apto 10");

        dto.setEnderecos(List.of(end));
        return dto;
    }

    private Pessoa criarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setIdade(30);
        pessoa.setTelefone("11999999999");
        pessoa.setScore(500);
        pessoa.setAtivo(true);
        return pessoa;
    }

    private EnderecoDTO criarEnderecoViaCep() {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setCep("01001000");
        dto.setEstado("SP");
        dto.setCidade("São Paulo");
        dto.setBairro("Sé");
        dto.setLogradouro("Praça da Sé");
        return dto;
    }
}
