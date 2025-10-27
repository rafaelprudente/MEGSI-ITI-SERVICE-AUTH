package pt.iti.umdrive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pt.iti.umdrive.entities.UserEntity;
import pt.iti.umdrive.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity;

        if (!isUUID(username)) {
            userEntity = userRepository.findByUsername(username);
        } else {
            userEntity = userRepository.findById(UUID.fromString(username)).orElse(null);
        }

        if (userEntity == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.emptyList()
        );
    }

    private boolean isUUID(String username) {
        try {
            UUID.fromString(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
