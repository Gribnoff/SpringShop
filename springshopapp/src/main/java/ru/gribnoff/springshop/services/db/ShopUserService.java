package ru.gribnoff.springshop.services.db;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gribnoff.springshop.persistence.entities.ShopUser;
import ru.gribnoff.springshop.persistence.entities.enums.Role;
import ru.gribnoff.springshop.persistence.repositories.ShopUserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ShopUserService implements UserDetailsService {
    private final ShopUserRepository shopUserRepository;

    public ShopUser findShopUserByPhone(String phone) {
        return shopUserRepository.findShopUserByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ShopUser shopUser = findShopUserByPhone(username);
        if (shopUser == null)
            throw new UsernameNotFoundException("User not found!");
        return new User(shopUser.getPhone(), shopUser.getPassword(), mapRolesToAuthorities(shopUser.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        return role != null ?
                new ArrayList<GrantedAuthority>() {{add((GrantedAuthority) role::name);}} :
                new ArrayList<>();
    }

    public boolean userExists(String phone) {
        return shopUserRepository.existsByPhone(phone);
    }
}
