package pt.iti.umdrive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.iti.umdrive.entities.UserEntity;
import pt.iti.umdrive.model.AuthorityModel;
import pt.iti.umdrive.model.UserModel;
import pt.iti.umdrive.repository.UserRepository;
import pt.megsi.fwk.utils.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public String generateJwtToken(UserModel userModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userModel.getUsername(),
                        userModel.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        userModel.setId(userEntity.getDetails().getId());
        userModel.setAuthorities(userEntity.getAuthorities().stream().map(a -> AuthorityModel.builder().name(a.getAuthority()).build()).toList());

        return jwtUtils.generateToken(userModel);
    }
}
