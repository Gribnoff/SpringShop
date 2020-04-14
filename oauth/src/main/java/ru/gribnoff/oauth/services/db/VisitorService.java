package ru.gribnoff.oauth.services.db;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gribnoff.oauth.persistence.repositories.VisitorRepository;

@Service
@RequiredArgsConstructor
public class VisitorService implements UserDetailsService {
    private final VisitorRepository visitorRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return visitorRepository.findByUsername(username).orElse(null);
    }
}
