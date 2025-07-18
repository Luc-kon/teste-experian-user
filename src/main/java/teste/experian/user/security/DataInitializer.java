package teste.experian.user.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import teste.experian.user.dto.EnderecoDTO;
import teste.experian.user.dto.PessoaRequestDTO;
import teste.experian.user.entity.User;
import teste.experian.user.repository.PessoaRepository;
import teste.experian.user.repository.UserRepository;
import teste.experian.user.service.PessoaService;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole(User.Role.valueOf("ADMIN"));
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword("user123");
            user.setRole(User.Role.valueOf("USER"));
            userRepository.save(user);

            System.out.println("Usuários 'admin' e 'user' criados com sucesso." );
        }

        if (pessoaRepository.count() == 0) {
            int idadeComum = 30;
            String nomeRepetido = "João Silva";
            String cepComum = "01001000";

            EnderecoDTO e1 = new EnderecoDTO();
            e1.setCep(cepComum);
            e1.setNumero("10");
            e1.setComplemento("casa");

            EnderecoDTO e2 = new EnderecoDTO();
            e2.setCep("01311000");
            e2.setNumero("22");
            e2.setComplemento("apto");

            pessoaService.criar(new PessoaRequestDTO(
                    nomeRepetido,
                    idadeComum,
                    "11995565789",
                    700,
                    Arrays.asList(e1, e2)
            ));

            EnderecoDTO e3 = new EnderecoDTO();
            e3.setCep("01311000");
            e3.setNumero("33");
            e3.setComplemento("fundos");

            pessoaService.criar(new PessoaRequestDTO(
                    nomeRepetido,
                    idadeComum,
                    "11998887777",
                    600,
                    List.of(e3)
            ));

            EnderecoDTO e4 = new EnderecoDTO();
            e4.setCep(cepComum);
            e4.setNumero("44");
            e4.setComplemento("apto");

            EnderecoDTO e5 = new EnderecoDTO();
            e5.setCep("04001001");
            e5.setNumero("55");
            e5.setComplemento("casa");

            pessoaService.criar(new PessoaRequestDTO(
                    "Maria Oliveira",
                    idadeComum,
                    "11997776666",
                    800,
                    Arrays.asList(e4, e5)
            ));

            EnderecoDTO e6 = new EnderecoDTO();
            e6.setCep("04001001");
            e6.setNumero("66");
            e6.setComplemento("casa");

            pessoaService.criar(new PessoaRequestDTO(
                    "Lucas Souza",
                    25,
                    "11996665555",
                    500,
                    List.of(e6)
            ));

            EnderecoDTO e7 = new EnderecoDTO();
            e7.setCep("03001001");
            e7.setNumero("77");
            e7.setComplemento("apto");

            EnderecoDTO e8 = new EnderecoDTO();
            e8.setCep("03101000");
            e8.setNumero("88");
            e8.setComplemento("casa");

            pessoaService.criar(new PessoaRequestDTO(
                    "Ana Costa",
                    28,
                    "11994443333",
                    900,
                    Arrays.asList(e7, e8)
            ));

            System.out.println("Pessoas de teste criadas com sucesso.");
        }

    }
}