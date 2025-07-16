package teste.experian.user.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.experian.user.dto.LoginRequestDTO;
import teste.experian.user.dto.LoginResponseDTO;
import teste.experian.user.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping()
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        LoginResponseDTO response = loginService.autenticar(loginDTO);
        return ResponseEntity.ok(response);
    }
}