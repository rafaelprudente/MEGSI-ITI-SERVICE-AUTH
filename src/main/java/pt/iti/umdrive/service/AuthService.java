package pt.iti.umdrive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.megsi.fwk.entities.UserEntity;
import pt.megsi.fwk.models.AuthorityModel;
import pt.megsi.fwk.models.UserModel;
import pt.megsi.fwk.repositories.UserRepository;
import pt.megsi.fwk.utils.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public String generateJwtToken(UserModel userModel) {
        UserEntity userEntity = userRepository.findByUsername(userModel.getUsername());

        if (userEntity == null) {
            throw new BadCredentialsException("Username not found!");
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userModel.getUsername(),
                            userModel.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Wrong Password!");
        }

        if (authentication != null) {
            userModel.setId(userEntity.getDetails().getId());
            userModel.setAuthorities(userEntity.getAuthorities().stream().map(a -> AuthorityModel.builder().name(a.getAuthority()).build()).toList());
        }

        return jwtUtils.generateToken(userModel);
    }
}
