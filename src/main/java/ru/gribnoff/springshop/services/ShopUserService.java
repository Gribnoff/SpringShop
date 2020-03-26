package ru.gribnoff.springshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gribnoff.springshop.persistence.entities.Role;
import ru.gribnoff.springshop.persistence.entities.ShopUser;
import ru.gribnoff.springshop.persistence.repositories.RoleRepository;
import ru.gribnoff.springshop.persistence.repositories.ShopUserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopUserService implements UserDetailsService {
    private final ShopUserRepository shopUserRepository;
    private final RoleRepository roleRepository;

    public ShopUser findShopUserByPhone(String phone) {
        return shopUserRepository.findShopUserByPhone(phone);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ShopUser shopUser = shopUserRepository.findShopUserByPhone(username);
        if (shopUser == null)
            throw new UsernameNotFoundException("User not found!");
        return new User(shopUser.getPhone(), shopUser.getPassword(), mapRolesToAuthorities(shopUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public boolean userExist(String phone) {
        return shopUserRepository.existsByPhone(phone);
    }
}
