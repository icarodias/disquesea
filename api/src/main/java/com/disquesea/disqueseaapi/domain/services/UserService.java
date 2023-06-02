package com.disquesea.disqueseaapi.domain.services;

import com.disquesea.disqueseaapi.domain.exceptions.EntityIsBeingUsedException;
import com.disquesea.disqueseaapi.domain.exceptions.ResourceNotFoundException;
import com.disquesea.disqueseaapi.domain.model.User;
import com.disquesea.disqueseaapi.domain.respositories.UserRepository;
import com.disquesea.disqueseaapi.security.components.EncoderUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final EncoderUtil encoderUtil;

    public User create(User user) {
        final boolean usernameAlreadyExists = repository.findByUsername(user.getUsername()).isPresent();

        if (usernameAlreadyExists) {
            throw new EntityIsBeingUsedException("Username already exists");
        }

        final String encodedPassword = encoderUtil.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);

        return repository.save(user);
    }

    public User full(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
