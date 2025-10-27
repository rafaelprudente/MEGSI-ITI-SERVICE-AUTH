package pt.iti.umdrive.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.iti.umdrive.model.UserModel;
import pt.iti.umdrive.service.AuthService;

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

    /*
    @PostMapping("/signup")
    public String registerUser(@RequestBody UserEntity userEntity) {
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            return "Error: Username is already taken!";
        }
        // Create new user's account
        UserEntity newUserEntity = new UserEntity(
                null,
                userEntity.getUsername(),
                encoder.encode(userEntity.getPassword())
        );
        userRepository.save(newUserEntity);
        return "User registered successfully!";
    }
    */
}
