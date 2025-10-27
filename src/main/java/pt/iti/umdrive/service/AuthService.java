package pt.iti.umdrive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.iti.umdrive.entities.UserEntity;
import pt.iti.umdrive.model.UserModel;
import pt.iti.umdrive.repository.UserRepository;
import pt.iti.umdrive.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtils;
    private final AuthenticationManager authenticationManager;

    public String generateJwtToken(UserModel userModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userModel.getUsername(),
                        userModel.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserEntity ue = userRepository.findByUsername(userDetails.getUsername());

        userModel.setId(ue.getId());

        return jwtUtils.generateToken(userModel);
    }
}
