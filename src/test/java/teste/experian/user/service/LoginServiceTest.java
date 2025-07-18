package teste.experian.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import teste.experian.user.dto.LoginRequestDTO;
import teste.experian.user.dto.LoginResponseDTO;
import teste.experian.user.entity.User;
import teste.experian.user.entity.User.Role;
import teste.experian.user.repository.UserRepository;
import teste.experian.user.security.JwtUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    private LoginService loginService;
    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        loginService = new LoginService();
        userRepository = mock(UserRepository.class);
        jwtUtil = mock(JwtUtil.class);

        ReflectionTestUtils.setField(loginService, "userRepository", userRepository);
        ReflectionTestUtils.setField(loginService, "jwtUtil", jwtUtil);
    }

    @Test
    void autenticar_DeveRetornarToken_QuandoCredenciaisValidas() {
        // Arrange
        String username = "usuario";
        String password = "senha";
        String tokenEsperado = "token.jwt";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.USER);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(jwtUtil.gerarToken(username, "USER")).thenReturn(tokenEsperado);

        LoginRequestDTO loginDTO = new LoginRequestDTO(username, password);

        // Act
        LoginResponseDTO response = loginService.autenticar(loginDTO);

        // Assert
        assertNotNull(response);
        assertEquals(tokenEsperado, response.getToken());
    }

    @Test
    void autenticar_DeveLancarExcecao_QuandoUsuarioNaoEncontrado() {
        // Arrange
        String username = "inexistente";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        LoginRequestDTO loginDTO = new LoginRequestDTO(username, "qualquer");

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> loginService.autenticar(loginDTO));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void autenticar_DeveLancarExcecao_QuandoSenhaInvalida() {
        // Arrange
        String username = "usuario";
        String senhaCorreta = "123";
        String senhaErrada = "errada";

        User user = new User();
        user.setUsername(username);
        user.setPassword(senhaCorreta);
        user.setRole(Role.USER);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        LoginRequestDTO loginDTO = new LoginRequestDTO(username, senhaErrada);

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> loginService.autenticar(loginDTO));

        assertEquals("Senha inválida", exception.getMessage());
    }
}
