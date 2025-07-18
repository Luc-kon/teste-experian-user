package teste.experian.user.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "minha-chave-secreta-bem-grande-para-hmac");
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", 3600000L); // 1 hora
    }

    @Test
    void gerarToken_DeveConterUsernameERole() {
        String token = jwtUtil.gerarToken("usuarioTeste", "USER");

        assertNotNull(token);
        assertTrue(jwtUtil.validarToken(token));
        assertEquals("usuarioTeste", jwtUtil.getUsername(token));
        assertEquals("USER", jwtUtil.getRole(token));
    }

    @Test
    void validarToken_DeveRetornarFalse_ParaTokenInvalido() {
        String tokenInvalido = "token.falso.aqui";
        assertFalse(jwtUtil.validarToken(tokenInvalido));
    }

    @Test
    void validarToken_DeveRetornarFalse_ParaTokenExpirado() throws InterruptedException {
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", 1L); // 1 milissegundo
        String token = jwtUtil.gerarToken("usuarioTeste", "USER");

        Thread.sleep(10); // espera o token expirar

        assertFalse(jwtUtil.validarToken(token));
    }
}
