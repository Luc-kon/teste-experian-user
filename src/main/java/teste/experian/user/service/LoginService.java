package teste.experian.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teste.experian.user.dto.LoginRequestDTO;
import teste.experian.user.dto.LoginResponseDTO;
import teste.experian.user.entity.User;
import teste.experian.user.repository.UserRepository;
import teste.experian.user.security.JwtUtil;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponseDTO autenticar(LoginRequestDTO loginDTO) {
        Optional<User> usuarioOpt = usuarioRepository.findByUsername(loginDTO.getUsername());

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        User user = usuarioOpt.get();

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtUtil.gerarToken(user.getUsername(), user.getRole().name());
        return new LoginResponseDTO(token);
    }
}