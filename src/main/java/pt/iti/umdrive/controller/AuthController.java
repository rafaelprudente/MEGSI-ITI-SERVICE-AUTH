package pt.iti.umdrive.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.iti.umdrive.service.AuthService;
import pt.megsi.fwk.models.UserModel;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestHeader String username, @RequestHeader String password) {
        return ResponseEntity.ok(authService.generateJwtToken(UserModel.builder().username(username).password(password).build()));
    }

    @Hidden
    @GetMapping("/validate")
    public ResponseEntity<String> validate() {
        return ResponseEntity.ok("");
    }
}
