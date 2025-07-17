package teste.experian.user.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import teste.experian.user.dto.EnderecoDTO;
import teste.experian.user.dto.PessoaDTO;
import teste.experian.user.dto.PessoaRequestDTO;
import teste.experian.user.entity.Endereco;
import teste.experian.user.entity.Pessoa;
import teste.experian.user.repository.EnderecoRepository;
import teste.experian.user.repository.PessoaRepository;
import teste.experian.user.util.ScoreDescricao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public PessoaDTO criar(PessoaRequestDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setIdade(dto.getIdade());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setScore(dto.getScore());
        pessoa.setAtivo(true);
        pessoa = pessoaRepository.save(pessoa);

        for (EnderecoDTO enderecoDTO : dto.getEnderecos()) {
            EnderecoDTO viaCep = buscarEnderecoViaCep(enderecoDTO.getCep());
            if (viaCep != null) {
                Endereco endereco = new Endereco();
                endereco.setCep(viaCep.getCep());
                endereco.setEstado(viaCep.getEstado());
                endereco.setCidade(viaCep.getCidade());
                endereco.setBairro(viaCep.getBairro());
                endereco.setLogradouro(viaCep.getLogradouro());
                endereco.setNumero(enderecoDTO.getNumero());
                endereco.setComplemento(enderecoDTO.getComplemento());
                endereco.setPessoa(pessoa);
                enderecoRepository.save(endereco);
            }
        }
        pessoa = pessoaRepository.findById(pessoa.getId()).orElseThrow();
        return converterParaDTO(pessoa);
    }

    public Page<PessoaDTO> listar(String nome, Integer idade, String cep, Pageable pageable) {
        Page<Pessoa> pessoas = pessoaRepository.buscarComFiltros(nome, idade, cep, pageable);
        return pessoas.map(this::converterParaDTO);
    }

    @Transactional
    public PessoaDTO atualizar(Long id, PessoaRequestDTO dto) {
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
        if (pessoaOpt.isEmpty()) return null;

        Pessoa pessoa = pessoaOpt.get();
        pessoa.setNome(dto.getNome());
        pessoa.setIdade(dto.getIdade());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setScore(dto.getScore());
        pessoaRepository.save(pessoa);

        // Endereços antigos mantidos (ou lógica extra pode ser adicionada para substituir)

        return converterParaDTO(pessoa);
    }

    @Transactional
    public boolean excluir(Long id) {
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
        if (pessoaOpt.isEmpty()) return false;

        Pessoa pessoa = pessoaOpt.get();
        pessoa.setAtivo(false);
        pessoaRepository.save(pessoa);
        return true;
    }

    private EnderecoDTO buscarEnderecoViaCep(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            return restTemplate.getForObject(url, EnderecoDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    private PessoaDTO converterParaDTO(Pessoa pessoa) {
        List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoa.getId());

        List<EnderecoDTO> enderecoDTOs = enderecos.stream().map(e -> {
            EnderecoDTO dto = new EnderecoDTO();
            dto.setId(e.getId());
            dto.setCep(e.getCep());
            dto.setEstado(e.getEstado());
            dto.setCidade(e.getCidade());
            dto.setBairro(e.getBairro());
            dto.setLogradouro(e.getLogradouro());
            dto.setNumero(e.getNumero());
            dto.setComplemento(e.getComplemento());
            return dto;
        }).collect(Collectors.toList());

        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setIdade(pessoa.getIdade());
        dto.setTelefone(pessoa.getTelefone());
        dto.setScore(pessoa.getScore());
        dto.setScoreDescricao(ScoreDescricao.getDescricao(pessoa.getScore()));
        dto.setEnderecos(enderecoDTOs);

        return dto;
    }
}