package ru.penlk.business.internal.implementation;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.penlk.business.internal.GrantedRoleService;

import java.util.Objects;

@Service
public class GrantedRoleServiceImpl implements GrantedRoleService {
    @Override
    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return false;
        }

        return auth.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), String.format("ROLE_%s", role)));
    }
}
